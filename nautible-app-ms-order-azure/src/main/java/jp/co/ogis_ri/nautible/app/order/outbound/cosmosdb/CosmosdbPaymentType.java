package jp.co.ogis_ri.nautible.app.order.outbound.cosmosdb;

/**
 * 支払い種別
 */
public enum CosmosdbPaymentType {
    /** クレジットカード */
    CREDIT,
    /** 着払い */
    CASH_ON_DELIVERY,
    /** コンビニ払い */
    CONVENNIENCE_STORE_PAYMENT;

}
