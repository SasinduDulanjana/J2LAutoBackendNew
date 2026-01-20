package com.example.smartPos.controllers;

import com.example.smartPos.controllers.requests.CustomerRequest;
import com.example.smartPos.controllers.requests.ExpensePaymentUpdateRequest;
import com.example.smartPos.controllers.requests.ExpenseRequest;
import com.example.smartPos.controllers.responses.ChequeDetailsResponse;
import com.example.smartPos.controllers.responses.CustomerResponse;
import com.example.smartPos.controllers.responses.ExpenseResponse;
import com.example.smartPos.repositories.model.PaymentDetails;
import com.example.smartPos.services.ICustomerService;
import com.example.smartPos.services.IPaymentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/payments")
public class PaymentController {

    IPaymentService paymentService;
    public PaymentController(IPaymentService paymentService) {
        this.paymentService = paymentService;
    }


    @GetMapping("/api/getAllChequeDetails")
    public List<ChequeDetailsResponse> getAllChequeDetails() {
        return paymentService.getAllChequeDetails();
    }

    @PutMapping("/api/updateChequeStatus/{chequeNo}/{status}")
    public void updateChequeStatus(@PathVariable String chequeNo, @PathVariable String status) {
        paymentService.updateChequeStatus(chequeNo, status);
    }

    @PostMapping("/api/saveExpense")
    public ExpenseResponse saveExpense(@RequestBody ExpenseRequest request) {
        return paymentService.createExpense(request);
    }

    @PostMapping("/api/updateExpensePaymentAmount")
    public ExpenseResponse updateExpensePaymentAmount(@RequestBody ExpensePaymentUpdateRequest request) {
        return paymentService.updateExpensePaymentAmount(request);
    }

    @GetMapping("/api/getAllExpenses")
    public List<ExpenseResponse> getAllExpenses() {
        return paymentService.getAllExpenses();
    }

    @GetMapping("/api/paymentDetailsOfExpenses/{expenseId}")
    public List<PaymentDetails> paymentDetailsOfExpenses(@PathVariable Integer expenseId) {
        return paymentService.paymentDetailsOfExpenses(expenseId);
    }

}
