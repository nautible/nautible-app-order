package jp.co.ogis_ri.nautible.app.order.outbound.rest;

import java.util.function.Function;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import io.dapr.client.DaprClient;
import io.dapr.client.DaprClientBuilder;
import io.dapr.client.domain.PublishEventRequestBuilder;
import jp.co.ogis_ri.nautible.app.order.domain.MessageSender;
import jp.co.ogis_ri.nautible.app.order.domain.Order;
import jp.co.ogis_ri.nautible.app.payment.client.rest.RestRejectCreatePayment;
import jp.co.ogis_ri.nautible.app.stock.client.rest.ProductAllocateDetail;
import jp.co.ogis_ri.nautible.app.stock.client.rest.StockApproveAllocateRequest;
import jp.co.ogis_ri.nautible.app.stock.client.rest.StockReserveAllocateRequest;

@ApplicationScoped
public class MessageSenderImpl implements MessageSender {

    private static final String STOCK_PUBSUB_NAME = "stock-pubsub";
    private static final String PAYMENT_PUBSUB_NAME = "payment-pubsub";
    // デフォルトのapplication/jsonだとsubscriber側でCloudEventに変換するとデータ部がMapになる。
    // steamにしてObjectMapperでマッピングした方が扱いやすい。
    private static final String PUBSUB_CONTENT_TYPE = "application/octet-stream";

    @Inject
    RestPaymentMapper paymentMapper;

    @Override
    public void reserveAllocateStock(Order order) {
        publish(STOCK_PUBSUB_NAME, "stock-reserve-allocate",
                new StockReserveAllocateRequest()
                        .requestId(order.getOrderNo())
                        .products(order.getProducts().stream()
                                .map(p -> new ProductAllocateDetail()
                                        .productId(p.getId())
                                        .quantity(p.getCount()))
                                .collect(Collectors.toList())));
    }

    @Override
    public void approveAllocateStock(Order order) {
        publish(STOCK_PUBSUB_NAME, "stock-approve-allocate",
                new StockApproveAllocateRequest()
                        .requestId(order.getOrderNo()));
    }

    @Override
    public void rejectAllocateStock(Order order) {
        publish(STOCK_PUBSUB_NAME, "stock-reject-allocate",
                new StockApproveAllocateRequest()
                        .requestId(order.getOrderNo()));
    }

    @Override
    public void createPayment(Order order) {
        publish(PAYMENT_PUBSUB_NAME, "payment-create",
                paymentMapper.orderToRestCreatePayment(order)
                        .paymentType(paymentMapper.paymentTypeToMethod(order.getPayment().getPaymentType())));
    }

    @Override
    public void rejectCreatePayment(Order order) {
        publish(PAYMENT_PUBSUB_NAME, "payment-reject-create",
                new RestRejectCreatePayment()
                        .requestId(order.getOrderNo()));
    }

    /**
     * SAGA。Daprのpubsubのpublishを行う。
     * @param pubsubName pubsubの名称
     * @param topic topic
     * @param data data
     */
    private void publish(String pubsubName, String topic, Object data) {
        // WARNING 分散トレーシングのIstio/Daprが共存できない。個別には実現できる。
        // DaprはW3Cのspecを採用、IstioはW3CのSpecには現状未対応。
        // IstioがW3Cに対応したらうまく共存できるかも？https://github.com/istio/istio/issues/23960
        executeDaprClient(
                c -> c.publishEvent(new PublishEventRequestBuilder(pubsubName, topic, data)
                        .withContentType(PUBSUB_CONTENT_TYPE).build())
                        .block());
    }

    private <R> R executeDaprClient(Function<DaprClient, R> func) {
        try (DaprClient client = new DaprClientBuilder().build()) {
            return func.apply(client);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}