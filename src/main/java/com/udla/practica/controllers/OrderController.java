package com.udla.practica.controllers;

import com.udla.practica.config.RabbitMQConfig;
import com.udla.practica.models.BillingCommand;
import com.udla.practica.models.OrderEvent;
import org.apache.camel.ProducerTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final ProducerTemplate producerTemplate;

    public OrderController(ProducerTemplate producerTemplate) {
        this.producerTemplate = producerTemplate;
    }

    @PostMapping("/billing")
    public ResponseEntity<String> sendBillingCommand(@RequestBody String jsonPayload) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("destination", "spring-rabbitmq:" + RabbitMQConfig.BILLING_QUEUE);
        headers.put("messageClass", BillingCommand.class);
        producerTemplate.sendBodyAndHeaders("direct:validateAndRoute", jsonPayload, headers);
        return ResponseEntity.ok("Comando de facturación enviado a validación.");
    }

    @PostMapping("/event")
    public ResponseEntity<String> sendOrderEvent(@RequestBody String jsonPayload) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("destination", "spring-rabbitmq:" + RabbitMQConfig.ORDERS_EXCHANGE);
        headers.put("messageClass", OrderEvent.class);
        producerTemplate.sendBodyAndHeaders("direct:validateAndRoute", jsonPayload, headers);
        return ResponseEntity.ok("Evento de orden enviado a validación.");
    }
}