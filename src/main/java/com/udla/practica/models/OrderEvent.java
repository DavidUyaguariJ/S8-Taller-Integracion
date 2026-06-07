package com.udla.practica.models;

import lombok.Data;

@Data
public class OrderEvent {
    private String eventId;
    private String eventType;
    private String occurredAt;
    private String source;
    private Payload payload;
}
