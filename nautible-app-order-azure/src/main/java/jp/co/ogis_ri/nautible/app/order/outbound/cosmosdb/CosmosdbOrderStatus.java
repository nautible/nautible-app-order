package jp.co.ogis_ri.nautible.app.order.outbound.cosmosdb;

/**
 * 注文ステータス
 */
public enum CosmosdbOrderStatus {
    /** 在庫引当予約処理中 */
    STOCK_RESERVE_ALLOCATE_DOING,
    /** 在庫引当承認処理中 */
    STOCK_APPROVE_ALLOCATE_DOING,
    /** 在庫引当却下処理中（SAGA補償トランザクション） */
    STOCK_REJECT_ALLOCATE_DOING,
    /** 受注処理中 */
    PAYMENT_CREATE_DOING,
    /** 受注取消し処理中（SAGA補償トランザクション） */
    PAYMENT_REJECT_CREATE_DOING,
    /** 注文完了 */
    ORDER_COMPLETE,
    /** 配送準備 */
    DELIVERY_IN_PREPARATION,
    /** 配送中 */
    IN_DELIVERY,
    /** 配送完了 */
    DELIVERY_COMPLETE,
    /** 却下 */
    REJECT,
    /** キャンセル */
    CANCEL,
    /** エラー */
    ERROR;
}
