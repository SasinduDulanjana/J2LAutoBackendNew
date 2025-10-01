package com.example.smartPos.services;

import com.example.smartPos.controllers.responses.ChequeDetailsResponse;
import com.example.smartPos.repositories.model.PaymentDetails;

import java.util.List;

public interface IPaymentService {
    List<ChequeDetailsResponse> getAllChequeDetails();

    void updateChequeStatus(String chequeId, String status);
}
