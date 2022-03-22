package jp.co.ogis_ri.nautible.app.order.domain;

/**
 * 支払い種別
 */
public enum PaymentType {
    /** クレジットカード */
    CREDIT,
    /** 着払い */
    CASH_ON_DELIVERY,
    /** コンビニ払い */
    CONVENNIENCE_STORE_PAYMENT;

}
