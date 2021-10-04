package jp.co.ogis_ri.nautible.app.order.outbound.cosmosdb;

import java.time.LocalDate;

import org.bson.codecs.pojo.annotations.BsonProperty;

/**
 * 配送ドメイン
 */
public class CosmosdbDelivery {
    /**
     * 配送番号。「D0000000001」
     **/
    @BsonProperty("DeliveryNumber")
    private String deliveryNumber;
    /** 配送予定日 */
    @BsonProperty("DeliveryPlanDate")
    private LocalDate deliveryPlanDate;
    /** 配送金額 */
    @BsonProperty("DeliveryPrice")
    private Integer deliveryPrice;
    /** 配送先宛名 */
    @BsonProperty("DeliveryName")
    private String deliveryName;
    /** 配送先郵便番号 */
    @BsonProperty("DeliveryPostCode")
    private String deliveryPostCode;
    /** 配送先住所1 */
    @BsonProperty("DeliveryAddress1")
    private String deliveryAddress1;
    /** 配送先住所2 */
    @BsonProperty("DeliveryAddress2")
    private String deliveryAddress2;
    /** 配送先電話番号 */
    @BsonProperty("DeliveryTel")
    private String deliveryTel;

    /**
     * 配送金額を設定する
     * @param deliveryPrice 配送金額
     * @return {@link CosmosdbDelivery}
     */
    public CosmosdbDelivery deliveryPrice(Integer deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
        return this;
    }

    /**
     * 配送金額を設定する
     * @return 配送金額
     */
    public Integer getDeliveryPrice() {
        return deliveryPrice;
    }

    /**
     * 配送金額を設定する
     * @param deliveryPrice 配送金額
     */
    public void setDeliveryPrice(Integer deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    /**
     * 配送番号を設定する
     * @param deliveryNumber 配送番号
     * @return {@link CosmosdbDelivery}
     */
    public CosmosdbDelivery deliveryNumber(String deliveryNumber) {
        this.deliveryNumber = deliveryNumber;
        return this;
    }

    /**
     * 配送番号を取得する
     * @return 配送番号
     */
    public String getDeliveryNumber() {
        return deliveryNumber;
    }

    /**
     * 配送番号を設定する
     * @param deliveryNumber 配送番号
     */
    public void setDeliveryNumber(String deliveryNumber) {
        this.deliveryNumber = deliveryNumber;
    }

    /**
     * 配送予定日を設定する
     * @param deliveryPlanDate 配送予定日
     * @return {@link CosmosdbDelivery}
     */
    public CosmosdbDelivery deliveryPlanDate(LocalDate deliveryPlanDate) {
        this.deliveryPlanDate = deliveryPlanDate;
        return this;
    }

    /**
     * 配送予定日を取得する
     * @return 配送予定日
     */
    public LocalDate getDeliveryPlanDate() {
        return deliveryPlanDate;
    }

    /**
     * 配送予定日を設定する
     * @param deliveryPlanDate 配送予定日
     */
    public void setDeliveryPlanDate(LocalDate deliveryPlanDate) {
        this.deliveryPlanDate = deliveryPlanDate;
    }

    /**
     * 配送先宛名を設定する
     * @param deliveryName 配送先宛名
     * @return {@link CosmosdbDelivery}
     */
    public CosmosdbDelivery deliveryName(String deliveryName) {
        this.deliveryName = deliveryName;
        return this;
    }

    /**
     * 配送先宛名を取得する
     * @return 配送先宛名
     */
    public String getDeliveryName() {
        return deliveryName;
    }

    /**
     * 配送先宛名を設定する
     * @param deliveryName 配送先宛名
     */
    public void setDeliveryName(String deliveryName) {
        this.deliveryName = deliveryName;
    }

    /**
     * 配送先郵便番号を設定する
     * @param deliveryPostCode 配送先郵便番号
     * @return {@link CosmosdbDelivery}
     */
    public CosmosdbDelivery deliveryPostCode(String deliveryPostCode) {
        this.deliveryPostCode = deliveryPostCode;
        return this;
    }

    /**
     * 配送先郵便番号を取得する
     * @return 配送先郵便番号
     */
    public String getDeliveryPostCode() {
        return deliveryPostCode;
    }

    /**
     * 配送先郵便番号を設定する
     * @param deliveryPostCode 配送先郵便番号
      */
    public void setDeliveryPostCode(String deliveryPostCode) {
        this.deliveryPostCode = deliveryPostCode;
    }

    /**
     * 配送先住所1を設定する
     * @param deliveryAddress1 配送先住所1
     * @return {@link CosmosdbDelivery}
     */
    public CosmosdbDelivery deliveryAddress1(String deliveryAddress1) {
        this.deliveryAddress1 = deliveryAddress1;
        return this;
    }

    /**
     * 配送先住所1を取得する
     * @return 配送先住所1
     */
    public String getDeliveryAddress1() {
        return deliveryAddress1;
    }

    /**
     * 配送先住所1を設定する
     * @param deliveryAddress1 配送先住所1
     */
    public void setDeliveryAddress1(String deliveryAddress1) {
        this.deliveryAddress1 = deliveryAddress1;
    }

    /**
     * 配送先住所2を設定する
     * @param deliveryAddress2 配送先住所2
     * @return {@link CosmosdbDelivery}
     */
    public CosmosdbDelivery deliveryAddress2(String deliveryAddress2) {
        this.deliveryAddress2 = deliveryAddress2;
        return this;
    }

    /**
     * 配送先住所2を取得する
     * @return 配送先住所2
     */
    public String getDeliveryAddress2() {
        return deliveryAddress2;
    }

    /**
     * 配送先住所2を設定する
     * @param deliveryAddress2 配送先住所1
     */
    public void setDeliveryAddress2(String deliveryAddress2) {
        this.deliveryAddress2 = deliveryAddress2;
    }

    /**
     * 配送先電話番号を設定する
     * @param deliveryTel 配送先電話番号
     * @return {@link CosmosdbDelivery}
     */
    public CosmosdbDelivery deliveryTel(String deliveryTel) {
        this.deliveryTel = deliveryTel;
        return this;
    }

    /**
     * 配送先電話番号を取得する
     * @return 配送先電話番号
     */
    public String getDeliveryTel() {
        return deliveryTel;
    }

    /**
     * 配送先電話番号を設定する
     * @param deliveryTel 配送先電話番号
     */
    public void setDeliveryTel(String deliveryTel) {
        this.deliveryTel = deliveryTel;
    }

}
