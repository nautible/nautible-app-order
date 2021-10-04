package jp.co.ogis_ri.nautible.app.order.outbound.dynamodb;

import java.util.Objects;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

/**
 * 商品ドメイン
 */
@DynamoDbBean
public class DynamodbProduct {

    /** Id */
    private Integer id;
    /** 金額 */
    private Integer price;
    /** 数量 */
    private Integer count;

    /**
     * Idを設定する
     * @param id Id
     * @return {@link DynamodbProduct}
     */
    public DynamodbProduct id(Integer id) {
        this.id = id;
        return this;
    }

    /**
     * Idを取得する
     * @return Id
     */
    @DynamoDbAttribute("Id")
    public Integer getId() {
        return id;
    }

    /**
     * Idを設定する
     * @param id Id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 金額を設定する
     * @param price 金額
     * @return {@link DynamodbProduct}
     */
    public DynamodbProduct price(Integer price) {
        this.price = price;
        return this;
    }

    /**
     * 金額を取得する
     * @return 金額
     */
    @DynamoDbAttribute("Price")
    public Integer getPrice() {
        return price;
    }

    /**
     * 金額を設定する
     * @param price 金額
     */
    public void setPrice(Integer price) {
        this.price = price;
    }

    /**
     * 数量を設定する
     * @param count 数量
     * @return {@link DynamodbProduct}
     */
    public DynamodbProduct count(Integer count) {
        this.count = count;
        return this;
    }

    /**
     * 数量を取得する
     * @return 数量
     */
    @DynamoDbAttribute("Count")
    public Integer getCount() {
        return count;
    }

    /**
     * 数量を設定する
     * @param count 数量
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DynamodbProduct product = (DynamodbProduct) o;
        return Objects.equals(this.id, product.id) &&
                Objects.equals(this.price, product.price) &&
                Objects.equals(this.count, product.count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price, count);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Product {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    price: ").append(toIndentedString(price)).append("\n");
        sb.append("    count: ").append(toIndentedString(count)).append("\n");
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
