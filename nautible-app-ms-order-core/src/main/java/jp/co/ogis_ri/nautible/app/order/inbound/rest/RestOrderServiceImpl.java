package jp.co.ogis_ri.nautible.app.order.inbound.rest;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.Validator;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.dapr.client.domain.CloudEvent;
import jp.co.ogis_ri.nautible.app.order.api.rest.CreateOrderReply;
import jp.co.ogis_ri.nautible.app.order.api.rest.DaprSubscribe;
import jp.co.ogis_ri.nautible.app.order.api.rest.RestCreateOrderRequest;
import jp.co.ogis_ri.nautible.app.order.api.rest.RestOrderService;
import jp.co.ogis_ri.nautible.app.order.api.rest.RestUpdateOrderRequest;
import jp.co.ogis_ri.nautible.app.order.core.rest.MDC;
import jp.co.ogis_ri.nautible.app.order.domain.Order;
import jp.co.ogis_ri.nautible.app.order.domain.OrderService;

/**
 * REST APIの注文サービス。REST APIのエンドポイント。
 */
@MDC
public class RestOrderServiceImpl implements RestOrderService {

    Logger LOG = Logger.getLogger(RestOrderServiceImpl.class.getName());
    /** orderのpubsub名 */
    private static final String ORDER_PUBSUB_NAME = "order-pubsub";

    @Inject
    OrderService service;
    @Inject
    RestOrderMapper mapper;
    @Inject
    ObjectMapper objectMapper;
    @Inject
    Validator validator;

    @Override
    public Response create(@Valid RestCreateOrderRequest request) {
        final Order ret = service.create(mapper.restCreateOrderRequestToOrder(request));
        return Response.ok(mapper.orderToRestOrder(ret)).build();
    }

    @Override
    public Response deleteByOrderNo(String orderNo) {
        service.deleteByOrderNo(orderNo);
        return Response.status(Status.NO_CONTENT).build();
    }

    @Override
    public Response getByOrderNo(String orderNo) {
        Order order = service.getByOrderNo(orderNo);
        return order == null ? Response.status(Status.NOT_FOUND).build()
                : Response.ok(mapper.orderToRestGetByOrderIdResponse(order)).build();
    }

    @Override
    public Response findByCustomerId(Integer customerId) {
        List<Order> orders = service.findByCustomerId(customerId);
        return (orders == null || orders.isEmpty()) ? Response.status(Status.NOT_FOUND).build()
                : Response.ok(mapper.orderToRestFindByCustomerIdResponse(orders)).build();
    }

    @Override
    public Response update(@Valid RestUpdateOrderRequest request) {
        Order orderRet = service.update(mapper.restUpdateOrderRequestToOrder(request));
        return orderRet == null ? Response.status(Status.NOT_FOUND).build()
                : Response.ok(mapper.orderToRestUpdateOrderResponse(orderRet)).build();
    }

    @Override
    public Response daprSubscribe() {
        // https://docs.dapr.io/developing-applications/building-blocks/pubsub/howto-publish-subscribe/#programmatic-subscriptions
        return Response.ok(List.of(
                new DaprSubscribe().pubsubname(ORDER_PUBSUB_NAME).topic("create-order-reply")
                        .route("/order/createOrderReply")))
                .build();
    }

    @Override
    public Response createOrderReply(@Valid byte[] body) {
        CreateOrderReply request = readCloudEventRequest(body, CreateOrderReply.class);
        LOG.log(Level.INFO, request.toString());
        // 受信オブジェクトのBeanValidation。DaprのsubscribeインターフェースがCloudEventのため独自に実行する必要がある。
        Set<ConstraintViolation<CreateOrderReply>> violations = validator.validate(request);
        if (violations.isEmpty() == false) {
            LOG.log(Level.SEVERE, new ConstraintViolationException(violations).getMessage());
            // 要件によってはpublisher側にエラーをreplyする
            return Response.status(Status.BAD_REQUEST).build();
        }
        service.createOrderReply(request.getRequestId(), request.getStatus(), request.getMessage());
        return Response.ok().build();
    }

    /**
     * DaprのsubscribeインターフェースであるCloudEventから、アプリケーションインターフェースの型に受信データを変換する
     * @param <R> アプリケーションインターフェースの型
     * @param body HttpRequestBody
     * @param clazz アプリケーションインターフェースの型
     * @return アプリケーションインターフェース
     */
    private <R> R readCloudEventRequest(byte[] body, Class<R> clazz) {
        try {
            CloudEvent event = CloudEvent.deserialize(body);
            return objectMapper.readValue(event.getBinaryData(), clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
