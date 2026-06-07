package com.udla.practica.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // Exchanges
    public static final String ORDERS_EXCHANGE = "orders.exchange";

    // Queues
    public static final String BILLING_QUEUE = "billing.queue";
    public static final String NOTIFICATION_QUEUE = "notification.queue";
    public static final String ANALYTICS_QUEUE = "analytics.queue";
    public static final String INVALID_MESSAGE_QUEUE = "invalid-message.queue";

    @Bean
    public FanoutExchange ordersExchange() {
        return new FanoutExchange(ORDERS_EXCHANGE, true, false);
    }

    @Bean
    public Queue billingQueue() {
        return new Queue(BILLING_QUEUE, true);
    }

    @Bean
    public Queue notificationQueue() {
        return new Queue(NOTIFICATION_QUEUE, true);
    }

    @Bean
    public Queue analyticsQueue() {
        return new Queue(ANALYTICS_QUEUE, true);
    }

    @Bean
    public Queue invalidQueue() {
        return new Queue(INVALID_MESSAGE_QUEUE, true);
    }

    @Bean
    public Binding bindingNotification() {
        return BindingBuilder.bind(notificationQueue()).to(ordersExchange());
    }

    @Bean
    public Binding bindingAnalytics() {
        return BindingBuilder.bind(analyticsQueue()).to(ordersExchange());
    }
}