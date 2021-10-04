package jp.co.ogis_ri.nautible.app.order.outbound.dynamodb;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import jp.co.ogis_ri.nautible.app.order.config.QuarkusMappingConfig;
import jp.co.ogis_ri.nautible.app.order.domain.Delivery;
import jp.co.ogis_ri.nautible.app.order.domain.Order;
import jp.co.ogis_ri.nautible.app.order.domain.Payment;
import jp.co.ogis_ri.nautible.app.order.domain.Product;

/**
 * DynamodbのEntityとDomainのマッピング定義
 */
@Mapper(config = QuarkusMappingConfig.class)
public interface DynamodbOrderMapper {

    DynamodbOrderMapper INSTANCE = Mappers.getMapper(DynamodbOrderMapper.class);

    DynamodbOrder orderToDynamodbOrder(Order order);

    DynamodbDelivery deliveryToDynamodbDelivery(Delivery delivery);

    DynamodbPayment paymentToDynamodbPayment(Payment payment);

    DynamodbProduct productToDynamodbProduct(Product product);

    List<DynamodbProduct> productToDynamodbProduct(List<Product> product);

    Order dynamodbOrderToOrder(DynamodbOrder order);

    Delivery dynamodbDeliveryToDelivery(DynamodbDelivery delivery);

    Payment dynamodbPaymentToPayment(DynamodbPayment payment);

    Product dynamodbProductToProduct(DynamodbProduct product);

    List<Product> dynamodbProductToProduct(List<DynamodbProduct> product);

    List<Order> dynamodbOrderToOrder(List<DynamodbOrder> order);

}
