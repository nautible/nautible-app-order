package jp.co.ogis_ri.nautible.app.order.domain;

import java.time.LocalDateTime;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

@ApplicationScoped
public class OrderService {

    @Inject
    Instance<OrderRepository> orderRepository;
    @Inject
    OrderSagaManager orderSagaManager;

    /**
     * SAGAのオーケストレーションパターンで注文を作成する<br>
     * <ui>
     * <li>注文データを永続化する
     * <li>在庫の引当予約をdaprのpubsubを利用してpublishする。
     * </ui>
     * @param order 注文
     * @return {@link Order}
     */
    public Order create(Order order) {
        // TODO 認証情報が無いので暫定で1固定
        order.setCustomerId(Integer.valueOf(1));
        order.setOrderDate(LocalDateTime.now());
        order = orderRepository.get().preCreate(order);
        // SAGA処理を委譲
        orderSagaManager.createOrder(order);

        order = orderRepository.get().create(order);

        return order;
    }

    /**
     * 注文を削除する
     * @param orderNo 注文No
     */
    public void deleteByOrderNo(String orderNo) {
        // 本当のアプリであれば在庫引当や受注の取り消し処理などが必要だが、コストがかかるので実装しない。
        orderRepository.get().delete(orderNo);
    }

    /**
     * 注文を取得する
     * @param orderNo 注文No
     * @return {@link Order}
     */
    public Order getByOrderNo(String orderNo) {
        return orderRepository.get().getByOrderNo(orderNo);
    }

    /**
     * 注文を取得する。最新の20件のみ取得する。
     * @param customerId 顧客Id
     * @return {@link Order}
     */
    public List<Order> findByCustomerId(Integer customerId) {
        return orderRepository.get().findByCustomerId(customerId);
    }

    /**
     * 注文を更新する
     * @param orderNo 注文No
     * @return {@link Order}
     */
    public Order update(Order order) {
        // 本当のアプリであれば在庫引当や受注の更新・取り消し処理などが必要だが、コストがかかるので実装しない。
        return orderRepository.get().update(order);
    }

    /**
     * SAGAの注文応答処理
     * 以下のフローに合わせてdaprのpubsubを利用してpublishする。
     * <p>
     * ０：在庫引当予約状態からスタート<br>
     * １：在庫引当予約→受注<br>
     * ２：受注→在庫引当承認<br>
     * ３：在庫引当承認→完了（配送準備へ）<br>
     * <p/>
     * @param requestId リクエストID
     * @param status 処理結果ステータス
     * @param message メッセージ
     */
    public void createOrderReply(String requestId, int status, String message) {
        Order order = orderRepository.get().getByOrderNo(requestId);
        // Saga処理の委譲
        order = orderSagaManager.handleCreateOrderReply(order, status, message);
        orderRepository.get().update(order);

    }

}
