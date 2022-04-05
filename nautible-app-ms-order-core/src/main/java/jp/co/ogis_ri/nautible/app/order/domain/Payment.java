package jp.co.ogis_ri.nautible.app.order.domain;

/**
 * 支払いドメイン
 */
public class Payment {

    /** 支払いId */
    private String paymentId;
    /** 支払い種別 */
    private PaymentType paymentType;

    /**
     * 支払いIdを設定する
     * @param paymentId 支払いId
     * @return {@link Order}
     */
    public Payment paymentId(String paymentId) {
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
     * @return {@link Order}
     */
    public Payment paymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
        return this;
    }

    /**
     * 支払い種別を取得する
     * @return 支払い種別
     */
    public PaymentType getPaymentType() {
        return paymentType;
    }

    /**
     * 支払い種別を設定する
     * @param paymentType 支払い種別
     */
    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

}
