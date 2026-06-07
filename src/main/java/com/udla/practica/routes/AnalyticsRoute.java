package com.udla.practica.routes;

import com.udla.practica.config.RabbitMQConfig;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class AnalyticsRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("spring-rabbitmq:" + RabbitMQConfig.ANALYTICS_QUEUE)
                .routeId("analytics-service-consumer")
                .log("[Analytics Service] Registrando métricas comerciales. Evento: ${body}");
    }
}
