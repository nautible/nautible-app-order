package jp.co.ogis_ri.nautible.app.order.outbound.cosmosdb;

import org.bson.codecs.pojo.annotations.BsonProperty;

/**
 * 支払いドメイン
 */
public class CosmosdbPayment {

    /** 支払いId */
    @BsonProperty("PaymentId")
    private String paymentId;
    /** 支払い種別 */
    @BsonProperty("PaymentType")
    private CosmosdbPaymentType paymentType;

    /**
     * 支払いIdを設定する
     * @param paymentId 支払いId
     * @return {@link CosmosdbOrder}
     */
    public CosmosdbPayment paymentId(String paymentId) {
        this.paymentId = paymentId;
        return this;
    }

    /**
     * 支払いIdを取得する
     * @return 支払いId
     */
    public String getPaymentId() {
        return paymentId;
    }

    /**
     * 支払いIdを設定する
     * @param paymentId 支払いId
     */
    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    /**
     * 支払い種別を設定する
     * @param paymentType 支払い種別
     * @return {@link CosmosdbOrder}
     */
    public CosmosdbPayment paymentType(CosmosdbPaymentType paymentType) {
        this.paymentType = paymentType;
        return this;
    }

    /**
     * 支払い種別を取得する
     * @return 支払い種別
     */
    public CosmosdbPaymentType getPaymentType() {
        return paymentType;
    }

    /**
     * 支払い種別を設定する
     * @param paymentType 支払い種別
     */
    public void setPaymentType(CosmosdbPaymentType paymentType) {
        this.paymentType = paymentType;
    }

}
