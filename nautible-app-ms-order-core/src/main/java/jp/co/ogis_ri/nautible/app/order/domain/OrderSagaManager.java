package jp.co.ogis_ri.nautible.app.order.domain;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * 注文のSAGAマネージャ
 */
@ApplicationScoped
public class OrderSagaManager {

    @Inject
    MessageSender messageSender;

    /**
     * 注文作成に関連するサービス呼び出しをpublishする
     * @param order {@link Order}
     */
    public Order createOrder(Order order) {
        order.setOrderStatus(OrderStatus.STOCK_RESERVE_ALLOCATE_DOING);
        messageSender.reserveAllocateStock(order);
        return order;
    }

    /**
     * 注文応答処理
     * <p>
     * 処理フロー<br>
     * ０：在庫引当予約状態からスタート<br>
     * １：在庫引当予約→受注<br>
     * ２：受注→在庫引当承認<br>
     * ３：在庫引当承認→完了（配送準備へ）<br>
     * </p>
     * @param order {@link Order}
     * @param processResultStatus 処理結果ステータス
     * @param message メッセージ
     * @return {@link Order}
     */
    public Order handleCreateOrderReply(Order order, int processResultStatus, String message) {
        order.setMemo(message);
        if (processResultStatus == 200) {
            // 正常終了時のSaga処理の委譲
            return handleCreateOrderReplyOK(order);
        } else {
            // 異常終了時のSaga処理の委譲
            return handleCreateOrderReplyFail(order);
        }
    }

    /**
     * 正常終了時の注文応答処理
     * @param order {@link Order}
     * @return {@link Order}
     */
    private Order handleCreateOrderReplyOK(Order order) {
        // 正常終了時。SAGAの処理実行を制御する
        switch (order.getOrderStatus()) {
        // ####################################
        // ##  正常処理のフロー  ##
        // ####################################
        case STOCK_RESERVE_ALLOCATE_DOING:
            // 決済呼び出し
            order.setOrderStatus(OrderStatus.PAYMENT_CREATE_DOING);
            messageSender.createPayment(order);
            break;
        case PAYMENT_CREATE_DOING:
            //在庫引当承認呼び出し
            order.setOrderStatus(OrderStatus.STOCK_APPROVE_ALLOCATE_DOING);
            messageSender.approveAllocateStock(order);
            break;

        case STOCK_APPROVE_ALLOCATE_DOING:
            order.setOrderStatus(OrderStatus.DELIVERY_IN_PREPARATION);
            break;
        // ####################################
        // ##  補償トランザクションのフロー  ##
        // ####################################
        //SAGAの補償トランザクションを正常時と逆の順序で呼び出す
        case PAYMENT_REJECT_CREATE_DOING:
            order.setOrderStatus(OrderStatus.STOCK_REJECT_ALLOCATE_DOING);
            messageSender.rejectAllocateStock(order);
            break;
        case STOCK_REJECT_ALLOCATE_DOING:
            order.setOrderStatus(OrderStatus.REJECT);
            break;
        default:
            // no ope
        }
        return order;
    }

    private Order handleCreateOrderReplyFail(Order order) {
        // 異常終了時。SAGAの補償トランザクションを正常時と逆の順序で呼び出す
        switch (order.getOrderStatus()) {
        case STOCK_APPROVE_ALLOCATE_DOING:
            // 受注の取り消し
            order.setOrderStatus(OrderStatus.PAYMENT_REJECT_CREATE_DOING);
            messageSender.rejectCreatePayment(order);
            break;
        case PAYMENT_CREATE_DOING:
            // 在庫引当の補償トランザクション呼び出し
            order.setOrderStatus(OrderStatus.STOCK_REJECT_ALLOCATE_DOING);
            messageSender.rejectAllocateStock(order);
            break;
        case STOCK_RESERVE_ALLOCATE_DOING:
            // フローの最初で失敗した場合は、補償トランザクションの呼び出しは不要
        case STOCK_REJECT_ALLOCATE_DOING:
            // rejectの失敗はこれ以上対応できない
            order.setOrderStatus(OrderStatus.ERROR);
        default:
            // no ope
        }
        return order;
    }

}
