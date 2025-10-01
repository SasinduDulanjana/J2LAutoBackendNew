package com.example.smartPos.controllers;

import com.example.smartPos.controllers.requests.CustomerRequest;
import com.example.smartPos.controllers.responses.ChequeDetailsResponse;
import com.example.smartPos.controllers.responses.CustomerResponse;
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
}
