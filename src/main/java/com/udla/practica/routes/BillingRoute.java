package com.udla.practica.routes;

import com.udla.practica.config.RabbitMQConfig;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class BillingRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("spring-rabbitmq:" + RabbitMQConfig.BILLING_QUEUE)
                .routeId("billing-service-consumer")
                .log("[Billing Service] Procesando Facturación. Cuerpo: ${body}");
    }
}