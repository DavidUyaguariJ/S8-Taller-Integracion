package com.udla.practica.routes;

import com.udla.practica.config.RabbitMQConfig;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class NotificationRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("spring-rabbitmq:" + RabbitMQConfig.NOTIFICATION_QUEUE)
                .routeId("notification-service-consumer")
                .log("[Notification Service] Enviando correo al cliente. Evento: ${body}");
    }
}