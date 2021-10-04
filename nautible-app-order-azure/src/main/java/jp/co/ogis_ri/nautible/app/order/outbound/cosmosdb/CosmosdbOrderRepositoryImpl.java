package jp.co.ogis_ri.nautible.app.order.outbound.cosmosdb;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;

import io.quarkus.mongodb.panache.PanacheMongoRepositoryBase;
import jp.co.ogis_ri.nautible.app.order.domain.Order;
import jp.co.ogis_ri.nautible.app.order.domain.OrderRepository;

/**
 * 注文レポジトリの実装クラス
 */
@Named("CosmosdbOrderRepositoryImpl")
@ApplicationScoped
public class CosmosdbOrderRepositoryImpl
        implements OrderRepository, PanacheMongoRepositoryBase<CosmosdbOrder, Integer> {
    /** 注文テーブル名 */
    private static final String ORDER_TABLE_NAME = "Order";

    @Inject
    MongoClient mongoClient;
    @Inject
    CosmosdbOrderMapper cosmosdbOrderMapper;

    @Override
    public Order getByOrderNo(String orderNo) {
        return cosmosdbOrderMapper.cosmosdbOrderToOrder((CosmosdbOrder) find("OrderNo", orderNo).firstResult());
    }

    @Override
    public List<Order> findByCustomerId(Integer customerId) {
        return cosmosdbOrderMapper.cosmosdbOrderToOrder(find("CustomerId", customerId).list());
    }

    @Override
    public Order preCreate(Order order) {
        if (StringUtils.isNotEmpty(order.getOrderNo())) {
            return order;
        }
        int sequence = getSequenceNumber(ORDER_TABLE_NAME);
        order.setOrderNo("O" + String.format("%010d", sequence));
        return order;
    }

    @Override
    public Order create(Order order) {
        persist(cosmosdbOrderMapper.orderToCosmosdbOrder(order));
        return order;
    }

    @Override
    public void delete(String orderNo) {
        delete("OrderNo", orderNo);
    }

    @Override
    public Order update(Order order) {
        update(cosmosdbOrderMapper.orderToCosmosdbOrder(order));
        return order;
    }

    /**
     * Mongodbのfunctionでシーケンスを発番する。
     * @param tableName テーブル名
     * @return シーケンス
     */
    private int getSequenceNumber(String tableName) {
        // 本来はマイクロサービスの管理単位を跨ぐような（データベースを跨ぐような）DBアクセスは禁止。
        // サービス毎にシーケンスCollectionを作成するとCosmosdbのコストが高くなる、また作業簡略化のためCommonDBへのアクセスを行う。
        // 時間ができたら共通サービスを作成して発番機能を作る。
        Document result = mongoClient.getDatabase("Common").getCollection("Sequence").findOneAndUpdate(
                Filters.eq("_id", tableName),
                new Document("$inc", new Document("SequenceNumber", 1)),
                new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER));
        return result.getInteger("SequenceNumber");
    }

}
