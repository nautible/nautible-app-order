package jp.co.ogis_ri.nautible.app.order.outbound.cosmosdb;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import jp.co.ogis_ri.nautible.app.order.config.QuarkusMappingConfig;
import jp.co.ogis_ri.nautible.app.order.domain.Delivery;
import jp.co.ogis_ri.nautible.app.order.domain.Order;
import jp.co.ogis_ri.nautible.app.order.domain.Payment;
import jp.co.ogis_ri.nautible.app.order.domain.Product;

/**
 * CosmosdbのEntityとDomainのマッピング定義
 */
@Mapper(config = QuarkusMappingConfig.class)
public interface CosmosdbOrderMapper {

    CosmosdbOrderMapper INSTANCE = Mappers.getMapper(CosmosdbOrderMapper.class);

    @Mapping(target = "id", expression = "java(Integer.parseInt(order.getOrderNo().substring(1)))")
    CosmosdbOrder orderToCosmosdbOrder(Order order);

    CosmosdbDelivery deliveryToCosmosdbDelivery(Delivery delivery);

    CosmosdbPayment paymentToCosmosdbPayment(Payment payment);

    CosmosdbProduct productToCosmosdbProduct(Product product);

    List<CosmosdbProduct> productToCosmosdbProduct(List<Product> product);

    Order cosmosdbOrderToOrder(CosmosdbOrder order);

    Delivery cosmosdbDeliveryToDelivery(CosmosdbDelivery delivery);

    Payment cosmosdbPaymentToPayment(CosmosdbPayment payment);

    Product cosmosdbProductToProduct(CosmosdbProduct product);

    List<Product> cosmosdbProductToProduct(List<CosmosdbProduct> product);

    List<Order> cosmosdbOrderToOrder(List<CosmosdbOrder> order);

}
