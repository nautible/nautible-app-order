package jp.co.ogis_ri.nautible.app.order.domain;

import java.util.List;

/**
 * 注文レポジトリ
 */
public interface OrderRepository {
    /**
     * 注文を取得する
     * @param orderNo 注文No
     * @return {@link Order}
     */
    Order getByOrderNo(String orderNo);

    /**
     * 注文を取得する。最新の20件のみ取得する。
     * @param customerId 顧客Id
     * @return {@link Order}のList
     */
    List<Order> findByCustomerId(Integer customerId);

    /**
     * 注文を作成事前処理(シーケンスの発番)
     * @param order 注文
     * @return {@link Order}
     */
    Order preCreate(Order order);

    /**
     * 注文を作成する
     * @param order 注文
     * @return {@link Order}
     */
    Order create(Order order);

    /**
     * 注文を削除する
     * @param orderNo 注文No
     */
    void delete(String orderNo);

    /**
     * 注文を更新する
     * @param order 注文
     * @return {@link Order}
     */
    Order update(Order order);

}
