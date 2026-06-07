package com.udla.practica.routes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.udla.practica.config.RabbitMQConfig;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
public class ValidationRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("direct:validateAndRoute")
                .routeId("validation-route")
                .choice()
                .when(exchange -> {
                    try {
                        String body = exchange.getIn().getBody(String.class);
                        ObjectMapper mapper = new ObjectMapper();
                        Map<?, ?> map = mapper.readValue(body, Map.class);
                        String orderId = (String) map.get("orderId");
                        String customerId = (String) map.get("customerId");
                        Object totalObj = map.get("total");
                        if (map.containsKey("payload")) {
                            Map<?, ?> payload = (Map<?, ?>) map.get("payload");
                            orderId = (String) payload.get("orderId");
                            customerId = (String) payload.get("customerId");
                            totalObj = payload.get("total");
                        }
                        Double total = totalObj != null ? Double.valueOf(totalObj.toString()) : null;
                        return orderId == null || orderId.isEmpty() ||
                                customerId == null || customerId.isEmpty() ||
                                total == null || total <= 0;

                    } catch (Exception e) {
                        return true;
                    }
                })
                .log("Mensaje Inválido detectado. Redirigiendo a cola de errores.")
                .to("spring-rabbitmq:" + RabbitMQConfig.INVALID_MESSAGE_QUEUE)
                .otherwise()
                .log("Mensaje Válido. Enviando a su respectivo canal.")
                .recipientList(header("destination"))
                .end();
    }
}
