package com.example.smartPos.services.impl;

import com.example.smartPos.controllers.responses.ChequeDetailsResponse;
import com.example.smartPos.repositories.PaymentDetailsRepository;
import com.example.smartPos.repositories.model.PaymentDetails;
import com.example.smartPos.services.IPaymentService;
import com.example.smartPos.util.PaymentStatus;
import com.example.smartPos.util.PaymentType;
import com.example.smartPos.util.ReferenceType;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class PaymentServiceImpl implements IPaymentService {

    private final PaymentDetailsRepository paymentDetailsRepository;

    public PaymentServiceImpl(PaymentDetailsRepository paymentDetailsRepository) {
        this.paymentDetailsRepository = paymentDetailsRepository;
    }

    @Override
    public List<ChequeDetailsResponse> getAllChequeDetails() {
        return paymentDetailsRepository.findAllChequeDetails().stream().map(chequeDetail -> {
            ChequeDetailsResponse chequeDetailsResponse = new ChequeDetailsResponse();
            chequeDetailsResponse.setChequeNo(chequeDetail.getChequeNo());
            chequeDetailsResponse.setBankName(chequeDetail.getBankName());
            if (chequeDetail.getPayment().getPaymentType().equals(PaymentType.RECEIPT) && chequeDetail.getPayment().getReferenceType().equals(ReferenceType.SALE)) {
                chequeDetailsResponse.setType("RECEIVED");
                chequeDetailsResponse.setCustomerOrSupplierName(chequeDetail.getPayment().getCustomer().getName());
            } else if(chequeDetail.getPayment().getPaymentType().equals(PaymentType.PAYMENT) && chequeDetail.getPayment().getReferenceType().equals(ReferenceType.PURCHASE)) {
                chequeDetailsResponse.setType("ISSUED");
                chequeDetailsResponse.setCustomerOrSupplierName(chequeDetail.getPayment().getSupplier().getName());
            }
            chequeDetailsResponse.setAmount(chequeDetail.getAmount());
            SimpleDateFormat sm = new SimpleDateFormat("dd-MM-yyyy");
            chequeDetailsResponse.setIssueDate(sm.format(chequeDetail.getPaymentDate()));
            chequeDetailsResponse.setDueDate(chequeDetail.getChequeDate());
            chequeDetailsResponse.setStatus(chequeDetail.getPaymentStatus().name());
            return chequeDetailsResponse;
        }).toList();
    }

    @Override
    public void updateChequeStatus(String chequeNo, String status) {
        PaymentDetails paymentDetails = paymentDetailsRepository.findByChequeNo(chequeNo)
                .orElseThrow(() -> new IllegalArgumentException("Cheque not found with ID: " + chequeNo));

        paymentDetails.setPaymentStatus(PaymentStatus.valueOf(status.toUpperCase()));
        paymentDetailsRepository.save(paymentDetails);
    }
}
