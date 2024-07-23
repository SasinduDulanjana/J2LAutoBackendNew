package com.example.smartPos.controllers.responses;

import lombok.Data;

@Data
public class PaymentResponse {
    private Integer paymentId;
    private String saleId;
    private String amount;
    private String paymentMethod;
}
