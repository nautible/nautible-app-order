package jp.co.ogis_ri.nautible.app.order.outbound.dynamodb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import jp.co.ogis_ri.nautible.app.order.domain.Order;
import jp.co.ogis_ri.nautible.app.order.domain.OrderRepository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeAction;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.AttributeValueUpdate;
import software.amazon.awssdk.services.dynamodb.model.ReturnValue;
import software.amazon.awssdk.services.dynamodb.model.UpdateItemRequest;
import software.amazon.awssdk.services.dynamodb.model.UpdateItemResponse;

/**
 * 注文レポジトリの実装クラス
 */
@Named("DynamodbOrderRepositoryImpl")
@ApplicationScoped
public class DynamodbOrderRepositoryImpl implements OrderRepository {
    /** 注文テーブル名 */
    private static final String ORDER_TABLE_NAME = "Order";

    @Inject
    DynamoDbClient dynamoDB;
    @Inject
    DynamodbOrderMapper dynamodbOrderMapper;

    @Override
    public Order getByOrderNo(String orderNo) {
        Key key = Key.builder().partitionValue(orderNo).build();
        DynamodbOrder result = getTable(ORDER_TABLE_NAME, DynamodbOrder.class).getItem(r -> r.key(key));
        return dynamodbOrderMapper.dynamodbOrderToOrder(result);
    }

    @Override
    public List<Order> findByCustomerId(Integer customerId) {
        Key key = Key.builder().partitionValue(customerId).build();
        return dynamodbOrderMapper
                .dynamodbOrderToOrder(getTable(ORDER_TABLE_NAME, DynamodbOrder.class).index("GSI-CustomerId")
                        .query(QueryEnhancedRequest.builder().queryConditional(QueryConditional.keyEqualTo(key))
                                // 最新の20件のみ取得
                                .limit(20)
                                .scanIndexForward(false)
                                .build())//
                        .iterator().next().items());
        //.stream().map(p -> p.items()).flatMap(l -> l.stream()).collect(Collectors.toList());
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
        order = preCreate(order);
        getTable(ORDER_TABLE_NAME, DynamodbOrder.class).putItem(dynamodbOrderMapper.orderToDynamodbOrder(order));
        return order;
    }

    @Override
    public void delete(String orderNo) {
        Key key = Key.builder().partitionValue(orderNo).build();
        getTable(ORDER_TABLE_NAME, DynamodbOrder.class).deleteItem(key);
    }

    @Override
    public Order update(Order order) {
        getTable(ORDER_TABLE_NAME, DynamodbOrder.class).updateItem(dynamodbOrderMapper.orderToDynamodbOrder(order));
        return order;
    }

    /**
     * Dyanmodbのアトミックカウンタでシーケンスを発番する。
     * @param tableName テーブル名
     * @return シーケンス
     */
    private int getSequenceNumber(String tableName) {
        Map<String, AttributeValue> key = new HashMap<>();
        key.put("Name", AttributeValue.builder().s(tableName).build());
        Map<String, AttributeValueUpdate> update = new HashMap<>();
        update.put("SequenceNumber", AttributeValueUpdate.builder().value(AttributeValue.builder().n("1").build())
                .action(AttributeAction.ADD).build());
        UpdateItemRequest updateRequest = UpdateItemRequest.builder().tableName("Sequence").key(key)
                .attributeUpdates(update).returnValues(ReturnValue.UPDATED_NEW).build();
        UpdateItemResponse updateResponse = dynamoDB.updateItem(updateRequest);
        return Integer.parseInt(updateResponse.attributes().get("SequenceNumber").n());
    }

    /**
     * DynamoDbTableを取得する
     * @param <E>
     * @param tableName テーブル名
     * @param bean DynamoDbBean
     * @return {@link DynamoDbTable}
     */
    private <E> DynamoDbTable<E> getTable(String tableName, Class<E> bean) {
        DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder().dynamoDbClient(dynamoDB).build();
        DynamoDbTable<E> mappedTable = enhancedClient.table(tableName, TableSchema.fromBean(bean));
        return mappedTable;
    }

}
