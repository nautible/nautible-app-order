package jp.co.ogis_ri.nautible.app.order.outbound.rest;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ValueMapping;
import org.mapstruct.factory.Mappers;

import jp.co.ogis_ri.nautible.app.order.config.QuarkusMappingConfig;
import jp.co.ogis_ri.nautible.app.order.domain.Order;
import jp.co.ogis_ri.nautible.app.order.domain.PaymentType;
import jp.co.ogis_ri.nautible.app.payment.client.rest.RestCreatePayment;

/**
 * REST APIのRequset/ResponseオブジェクトとDomainのマッピング定義
 */
@Mapper(config = QuarkusMappingConfig.class)
public interface RestPaymentMapper {

    RestPaymentMapper INSTANCE = Mappers.getMapper(RestPaymentMapper.class);

    @Mapping(target = "requestId", source = "orderNo")
    @Mapping(target = "totalPrice", source = "orderPrice")
    RestCreatePayment orderToRestCreatePayment(Order order);

    @ValueMapping(target = "01", source = "CREDIT")
    @ValueMapping(target = "02", source = "CASH_ON_DELIVERY")
    @ValueMapping(target = "03", source = "CONVENNIENCE_STORE_PAYMENT")
    String paymentTypeToMethod(PaymentType paymentType);

}
