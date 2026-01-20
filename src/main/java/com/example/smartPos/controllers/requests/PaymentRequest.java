package com.example.smartPos.controllers.requests;

import lombok.Data;

@Data
public class PaymentRequest {
    private Integer paymentId;
    private String saleId;
    private String amount;
    private String paymentMethod;
}
