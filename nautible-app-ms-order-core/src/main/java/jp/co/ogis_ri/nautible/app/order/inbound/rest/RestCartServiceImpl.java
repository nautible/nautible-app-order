package jp.co.ogis_ri.nautible.app.order.inbound.rest;

import java.util.function.Function;
import java.util.logging.Logger;

import javax.validation.Valid;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import io.dapr.client.DaprClient;
import io.dapr.client.DaprClientBuilder;
import io.dapr.client.domain.State;
import jp.co.ogis_ri.nautible.app.order.api.rest.RestCart;
import jp.co.ogis_ri.nautible.app.order.api.rest.RestCartService;
import jp.co.ogis_ri.nautible.app.order.core.rest.MDC;
import reactor.core.publisher.Mono;

/**
 * REST APIのカートサービス。REST APIのエンドポイント。
 */
@MDC
public class RestCartServiceImpl implements RestCartService {

    Logger LOG = Logger.getLogger(RestCartServiceImpl.class.getName());

    /** daprのstatestore名 */
    private static final String STATE_STORE_NAME = "order-statestore";

    /** statestoreのkey prefix */
    private static final String KEY_PREFIX = "cart:";

    @Override
    public Response getByCartId(Integer cartId) {
        RestCart result = executeDaprClient(c -> {
            Mono<State<RestCart>> retrievedMessageMono = c.getState(STATE_STORE_NAME, createKey(cartId),
                    RestCart.class);
            return retrievedMessageMono.block().getValue();
        });
        return Response.ok(result).build();
    }

    @Override
    public Response create(@Valid RestCart cart) {
        executeDaprClient(
                c -> c.saveState(STATE_STORE_NAME, createKey(cart.getId()),
                        cart).block());
        return Response.ok(cart).build();
    }

    @Override
    public Response update(@Valid RestCart cart) {
        return create(cart);
    }

    @Override
    public Response deleteByCartId(Integer cartId) {
        executeDaprClient(c -> c.deleteState(STATE_STORE_NAME, createKey(cartId)).block());
        return Response.ok(Status.ACCEPTED).build();
    }

    /**
     * statestoreのキーを作成する。「cart:123456789」
     * @param customerId 顧客Id
     * @return キー
     */
    private String createKey(Integer customerId) {
        return KEY_PREFIX + String.valueOf(customerId);
    }

    private <R> R executeDaprClient(Function<DaprClient, R> func) {
        try (DaprClient client = new DaprClientBuilder().build()) {
            return func.apply(client);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
