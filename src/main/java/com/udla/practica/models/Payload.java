package com.udla.practica.models;

import lombok.Data;

@Data
public class Payload {
    private String orderId;
    private String customerId;
    private Double total;
}