package jp.co.ogis_ri.nautible.app.order.core;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;
import org.eclipse.microprofile.health.Readiness;

import jp.co.ogis_ri.nautible.app.order.domain.OrderRepository;

/**
 * ヘルスチェック。 Dynamodbへの接続を検証する.{@link HealthCheck}。
 */
@Readiness
@ApplicationScoped
public class OrderHealthCheck implements HealthCheck {

    @Inject
    Instance<OrderRepository> orderRepository;

    @Override
    public HealthCheckResponse call() {
        HealthCheckResponseBuilder builder = HealthCheckResponse.named("Dynamodb connection");

        try {
            orderRepository.get().getByOrderNo("OrderNo");
            return builder.up().build();
        } catch (Throwable e) {
            return builder.down().build();
        }
    }

}
