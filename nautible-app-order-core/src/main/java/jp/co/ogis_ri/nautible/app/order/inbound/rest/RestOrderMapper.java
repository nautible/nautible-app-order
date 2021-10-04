package jp.co.ogis_ri.nautible.app.order.inbound.rest;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import jp.co.ogis_ri.nautible.app.order.api.rest.RestCreateOrderRequest;
import jp.co.ogis_ri.nautible.app.order.api.rest.RestCreateOrderResponse;
import jp.co.ogis_ri.nautible.app.order.api.rest.RestFindByCustomerIdResponse;
import jp.co.ogis_ri.nautible.app.order.api.rest.RestGetByOrderIdResponse;
import jp.co.ogis_ri.nautible.app.order.api.rest.RestOrder;
import jp.co.ogis_ri.nautible.app.order.api.rest.RestOrderDelivery;
import jp.co.ogis_ri.nautible.app.order.api.rest.RestOrderPayment;
import jp.co.ogis_ri.nautible.app.order.api.rest.RestOrderProduct;
import jp.co.ogis_ri.nautible.app.order.api.rest.RestUpdateOrderRequest;
import jp.co.ogis_ri.nautible.app.order.api.rest.RestUpdateOrderResponse;
import jp.co.ogis_ri.nautible.app.order.config.QuarkusMappingConfig;
import jp.co.ogis_ri.nautible.app.order.domain.Delivery;
import jp.co.ogis_ri.nautible.app.order.domain.Order;
import jp.co.ogis_ri.nautible.app.order.domain.Payment;
import jp.co.ogis_ri.nautible.app.order.domain.Product;

/**
 * REST APIのRequset/ResponseオブジェクトとDomainのマッピング定義
 */
@Mapper(config = QuarkusMappingConfig.class)
public interface RestOrderMapper {

    RestOrderMapper INSTANCE = Mappers.getMapper(RestOrderMapper.class);

    default RestCreateOrderResponse orderToRestCreateOrderResponse(Order order) {
        return new RestCreateOrderResponse().order(orderToRestOrder(order));
    }

    default RestUpdateOrderResponse orderToRestUpdateOrderResponse(Order order) {
        return new RestUpdateOrderResponse().order(orderToRestOrder(order));
    }

    default RestGetByOrderIdResponse orderToRestGetByOrderIdResponse(Order order) {
        return new RestGetByOrderIdResponse().order(orderToRestOrder(order));
    }

    default RestFindByCustomerIdResponse orderToRestFindByCustomerIdResponse(List<Order> orders) {
        return new RestFindByCustomerIdResponse().orders(orderToRestOrder(orders));
    }

    @Mapping(target = "delivery", ignore = true)
    @Mapping(target = "payment", ignore = true)
    RestOrder orderToRestOrder(Order order);

    List<RestOrder> orderToRestOrder(List<Order> order);

    RestOrderProduct productToRestProduct(Product product);

    List<RestOrderProduct> productToRestProduct(List<Product> product);

    @Mapping(target = "customerId", ignore = true)
    @Mapping(target = "memo", ignore = true)
    Order restOrderToOrder(RestOrder order);

    Product restProductToProduct(RestOrderProduct product);

    List<Product> restProductToProduct(List<RestOrderProduct> product);

    @Mapping(target = "customerId", ignore = true)
    @Mapping(target = "memo", ignore = true)
    Order restUpdateOrderRequestToOrder(RestUpdateOrderRequest order);

    @Mapping(target = "orderNo", ignore = true)
    @Mapping(target = "customerId", ignore = true)
    @Mapping(target = "memo", ignore = true)
    Order restCreateOrderRequestToOrder(RestCreateOrderRequest order);

    @Mapping(target = "deliveryPlanDate", source = "planDate")
    @Mapping(target = "deliveryName", source = "name")
    @Mapping(target = "deliveryPostCode", source = "postCode")
    @Mapping(target = "deliveryAddress1", source = "address1")
    @Mapping(target = "deliveryAddress2", source = "address2")
    @Mapping(target = "deliveryPrice", source = "price")
    @Mapping(target = "deliveryTel", source = "tel")
    Delivery restOrderDeliveryToDelivery(RestOrderDelivery restOrderDelivery);

    @Mapping(target = "paymentId", source = "id")
    Payment restOrderPaymentToPayment(RestOrderPayment restOrderPayment);

}
