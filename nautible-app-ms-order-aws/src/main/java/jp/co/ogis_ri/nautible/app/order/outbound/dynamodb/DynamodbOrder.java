package jp.co.ogis_ri.nautible.app.order.outbound.dynamodb;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jp.co.ogis_ri.nautible.app.order.domain.Order;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondaryPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondarySortKey;

/**
 * 注文ドメイン
 */
@DynamoDbBean
public class DynamodbOrder {

    /**
     * 注文番号。「O0000000001」
     **/
    private String orderNo;
    /** 顧客Id */
    private Integer customerId;
    /** 注文日 */
    private LocalDateTime orderDate;
    /** 注文金額 */
    private Integer orderPrice;
    /** 注文状況 */
    private DynamodbOrderStatus orderStatus;
    /** メモ */
    private String memo;
    /** 商品 */
    private List<DynamodbProduct> products = new ArrayList<>();
    /** 配送 */
    private DynamodbDelivery delivery;
    /** 支払い */
    private DynamodbPayment payment;

    /**
     * 注文番号を設定する
     * @param orderNo 注文番号
     * @return {@link DynamodbOrder}
     */
    public DynamodbOrder orderNo(String orderNo) {
        this.orderNo = orderNo;
        return this;
    }

    /**
     * 注文番号を取得する
     * @return 注文番号
     */
    @DynamoDbPartitionKey
    @DynamoDbSecondarySortKey(indexNames = { "GSI-CustomerId" })
    @DynamoDbAttribute("OrderNo")
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * 注文番号を設定する
     * @param orderNo 注文番号
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * 顧客Idを設定する
     * @param customerId 顧客Id
     * @return {@link DynamodbOrder}
     */
    public DynamodbOrder customerId(Integer customerId) {
        this.customerId = customerId;
        return this;
    }

    /**
     * 顧客Idを取得する
     * @return 顧客Id
     */
    @DynamoDbSecondaryPartitionKey(indexNames = { "GSI-CustomerId" })
    @DynamoDbAttribute("CustomerId")
    public Integer getCustomerId() {
        return customerId;
    }

    /**
     * 顧客Idを設定する
     * @param customerId 顧客Id
     */
    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    /**
     * 注文日を設定する
     * @param orderDate 注文日
     * @return {@link DynamodbOrder}
     */
    public DynamodbOrder orderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
        return this;
    }

    /**
     * 注文日を取得する
     * @return 注文日
     */
    @DynamoDbAttribute("OrderDate")
    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    /**
     * 注文日を設定する
     * @param orderDate 注文日
     */
    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    /**
     * 注文金額を設定する
     * @param orderPrice 注文金額
     * @return {@link DynamodbOrder}
     */
    public DynamodbOrder orderPrice(Integer orderPrice) {
        this.orderPrice = orderPrice;
        return this;
    }

    /**
     * 注文金額を取得する
     * @return 注文金額
     */
    @DynamoDbAttribute("OrderPrice")
    public Integer getOrderPrice() {
        return orderPrice;
    }

    /**
     * 注文金額を設定する
     * @param orderPrice 注文金額
     */
    public void setOrderPrice(Integer orderPrice) {
        this.orderPrice = orderPrice;
    }

    /**
     * 注文状況を設定する
     * @param orderStatus 注文状況
     * @return {@link DynamodbOrder}
     */
    public DynamodbOrder orderStatus(DynamodbOrderStatus orderStatus) {
        this.orderStatus = orderStatus;
        return this;
    }

    /**
     * 注文状況を取得する
     * @return 注文状況
     */
    @DynamoDbAttribute("OrderStatus")
    public DynamodbOrderStatus getOrderStatus() {
        return orderStatus;
    }

    /**
     * 注文状況を設定する
     * @param orderStatus 注文状況
     */
    public void setOrderStatus(DynamodbOrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    /**
     * メモを設定する
     * @param memo メモ
     * @return {@link Order}
     */
    public DynamodbOrder memo(String memo) {
        this.memo = memo;
        return this;
    }

    /**
     * メモを取得する
     * @return メモ
     */
    @DynamoDbAttribute("Memo")
    public String getMemo() {
        return memo;
    }

    /**
     * メモを設定する
     * @param memo メモ
     */
    public void setMemo(String memo) {
        this.memo = memo;
    }

    /**
     * 配送を設定する
     * @param delivery 配送
     * @return {@link DynamodbOrder}
     */
    public DynamodbOrder delivery(DynamodbDelivery delivery) {
        this.delivery = delivery;
        return this;
    }

    /**
     * 配送を取得する
     * @return 配送
     */
    @DynamoDbAttribute("Delivery")
    public DynamodbDelivery getDelivery() {
        return delivery;
    }

    /**
     * 配送を設定する
     * @param delivery 配送
     */
    public void setDelivery(DynamodbDelivery delivery) {
        this.delivery = delivery;
    }

    /**
     * 支払いを設定する
     * @param payment 支払い
     * @return {@link DynamodbPayment}
     */
    public DynamodbOrder payment(DynamodbPayment payment) {
        this.payment = payment;
        return this;
    }

    /**
     * 支払いを取得する
     * @return 支払い
     */
    @DynamoDbAttribute("Payment")
    public DynamodbPayment getPayment() {
        return payment;
    }

    /**
     * 支払いを設定する
     * @param payment 支払い
     */
    public void setPayment(DynamodbPayment payment) {
        this.payment = payment;
    }

    /**
     * 商品を設定する
     * @param products 商品
     * @return {@link Order}
     */
    public DynamodbOrder products(List<DynamodbProduct> products) {
        this.products = products;
        return this;
    }

    /**
     * 商品を取得する
     * @return 商品
     */
    @DynamoDbAttribute("Products")
    public List<DynamodbProduct> getProducts() {
        return products;
    }

    /**
     * 商品を設定する
     * @param products 商品
     */
    public void setProducts(List<DynamodbProduct> products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DynamodbOrder order = (DynamodbOrder) o;
        return Objects.equals(this.orderNo, order.orderNo) &&
                Objects.equals(this.orderDate, order.orderDate) &&
                Objects.equals(this.orderPrice, order.orderPrice) &&
                Objects.equals(this.orderStatus, order.orderStatus) &&
                Objects.equals(this.products, order.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderNo, orderDate, orderPrice, orderStatus, products);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Order {\n");

        sb.append("    orderNo: ").append(toIndentedString(orderNo)).append("\n");
        sb.append("    orderDate: ").append(toIndentedString(orderDate)).append("\n");
        sb.append("    orderPrice: ").append(toIndentedString(orderPrice)).append("\n");
        sb.append("    orderStatus: ").append(toIndentedString(orderStatus)).append("\n");
        sb.append("    products: ").append(toIndentedString(products)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }

}
