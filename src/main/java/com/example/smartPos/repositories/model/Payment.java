package com.example.smartPos.repositories.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "PAYMENT")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer paymentId;

    private String saleId;

    private String amount;

    private String paymentMethod;


}
