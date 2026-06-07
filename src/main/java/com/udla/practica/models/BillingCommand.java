package com.udla.practica.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@NotNull
@NotBlank
public class BillingCommand {
    private String messageId;
    private String messageType;
    private String orderId;
    private String customerId;
    private Double total;
}