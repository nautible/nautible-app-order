package jp.co.ogis_ri.nautible.app.order.outbound.dynamodb;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

/**
 * 支払いドメイン
 */
@DynamoDbBean
public class DynamodbPayment {

    /** 支払いId */
    private String paymentId;
    /** 支払い種別 */
    private DynamodbPaymentType paymentType;

    /**
     * 支払いIdを設定する
     * @param paymentId 支払いId
     * @return {@link DynamodbOrder}
     */
    public DynamodbPayment paymentId(String paymentId) {
        this.paymentId = paymentId;
        return this;
    }

    /**
     * 支払いIdを取得する
     * @return 支払いId
     */
    @DynamoDbAttribute("PaymentId")
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
     * @return {@link DynamodbOrder}
     */
    public DynamodbPayment paymentType(DynamodbPaymentType paymentType) {
        this.paymentType = paymentType;
        return this;
    }

    /**
     * 支払い種別を取得する
     * @return 支払い種別
     */
    @DynamoDbAttribute("PaymentType")
    public DynamodbPaymentType getPaymentType() {
        return paymentType;
    }

    /**
     * 支払い種別を設定する
     * @param paymentType 支払い種別
     */
    public void setPaymentType(DynamodbPaymentType paymentType) {
        this.paymentType = paymentType;
    }

}
