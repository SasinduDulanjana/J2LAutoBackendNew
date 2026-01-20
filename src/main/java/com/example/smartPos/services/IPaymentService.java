package com.example.smartPos.services;

import com.example.smartPos.controllers.requests.ExpensePaymentUpdateRequest;
import com.example.smartPos.controllers.requests.ExpenseRequest;
import com.example.smartPos.controllers.requests.PaymentRequest;
import com.example.smartPos.controllers.responses.ChequeDetailsResponse;
import com.example.smartPos.controllers.responses.ExpenseResponse;
import com.example.smartPos.controllers.responses.PaymentResponse;
import com.example.smartPos.repositories.model.PaymentDetails;

import java.util.List;

public interface IPaymentService {
    List<ChequeDetailsResponse> getAllChequeDetails();

    void updateChequeStatus(String chequeId, String status);

    ExpenseResponse createExpense(ExpenseRequest request);

    ExpenseResponse updateExpensePaymentAmount(ExpensePaymentUpdateRequest request);

    List<ExpenseResponse> getAllExpenses();

    List<PaymentDetails> paymentDetailsOfExpenses(Integer expenseId);
}
