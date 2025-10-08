package com.example.smartPos.services.impl;

import com.example.smartPos.controllers.requests.ExpenseRequest;
import com.example.smartPos.controllers.responses.ChequeDetailsResponse;
import com.example.smartPos.controllers.responses.ExpenseResponse;
import com.example.smartPos.mapper.ExpenseMapper;
import com.example.smartPos.repositories.ExpenseRepository;
import com.example.smartPos.repositories.PaymentDetailsRepository;
import com.example.smartPos.repositories.PaymentRepository;
import com.example.smartPos.repositories.model.Expense;
import com.example.smartPos.repositories.model.Payment;
import com.example.smartPos.repositories.model.PaymentDetails;
import com.example.smartPos.services.IPaymentService;
import com.example.smartPos.util.PaymentStatus;
import com.example.smartPos.util.PaymentType;
import com.example.smartPos.util.ReferenceType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class PaymentServiceImpl implements IPaymentService {

    private final PaymentDetailsRepository paymentDetailsRepository;

    private final ExpenseRepository expenseRepository;

    private final PaymentRepository paymentRepository;

    private final ExpenseMapper expenseMapper;

    public PaymentServiceImpl(PaymentDetailsRepository paymentDetailsRepository, ExpenseRepository expenseRepository, PaymentRepository paymentRepository, ExpenseMapper expenseMapper) {
        this.paymentDetailsRepository = paymentDetailsRepository;
        this.expenseRepository = expenseRepository;
        this.paymentRepository = paymentRepository;
        this.expenseMapper = expenseMapper;
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
            } else if(chequeDetail.getPayment().getPaymentType().equals(PaymentType.PAYMENT) && chequeDetail.getPayment().getReferenceType().equals(ReferenceType.EXPENSE)) {
                chequeDetailsResponse.setType("ISSUED");
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
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();

        PaymentDetails paymentDetails = paymentDetailsRepository.findByChequeNo(chequeNo)
                .orElseThrow(() -> new IllegalArgumentException("Cheque not found with ID: " + chequeNo));

        paymentDetails.setPaymentStatus(PaymentStatus.valueOf(status.toUpperCase()));
        paymentDetails.fillUpdated(currentUser);
        paymentDetailsRepository.save(paymentDetails);
    }

    @Override
    @Transactional
    public ExpenseResponse createExpense(ExpenseRequest request) {

        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        Expense expense = new Expense();
        expense.setDate(new Date());
        expense.setExpenseType(request.getExpenseType());
        expense.setDescription(request.getDescription());
        expense.setAmount(request.getAmount());
        expense.setReference(request.getReference());
        expense.setPaymentMethod(request.getPaymentMethod());
        expense.setBankName(request.getBankName());
        expense.setChequeDate(request.getChequeDate());
        expense.setChequeNumber(request.getChequeNumber());
        expense.fillNew(currentUser);
        Expense savedExpense = expenseRepository.save(expense);

        Payment payment = new Payment();
        payment.setPaymentDate(new Date());
        payment.setPaymentType(PaymentType.PAYMENT);
        payment.setReferenceId(savedExpense.getId().toString());
        payment.setReferenceType(ReferenceType.EXPENSE);
        payment.setRemarks("Expense Payment" + request.getExpenseType());
        payment.setTotalAmount(request.getAmount());
        payment.fillNew(currentUser);
        Payment savedPayment = paymentRepository.save(payment);

        PaymentDetails paymentDetails = new PaymentDetails();
        paymentDetails.setPayment(savedPayment);
        paymentDetails.setAmount(request.getAmount());
        paymentDetails.setPaymentMethod(request.getPaymentMethod());
        paymentDetails.setBankName(request.getBankName());
        paymentDetails.setChequeNo(request.getChequeNumber());
        paymentDetails.setChequeDate(request.getChequeDate());
        if (request.getPaymentMethod().equalsIgnoreCase("CHEQUE")) {
            paymentDetails.setPaymentStatus(PaymentStatus.PENDING);
        } else {
            paymentDetails.setPaymentStatus(PaymentStatus.CLEARED);
        }
        paymentDetails.setPaymentDate(new Date());
        paymentDetails.fillNew(currentUser);
        PaymentDetails savedPaymentDetails = paymentDetailsRepository.save(paymentDetails);

        ExpenseResponse expenseResponse = new ExpenseResponse();
        expenseResponse.setId(savedExpense.getId());
        expenseResponse.setPaymentId(savedPayment.getPaymentId());
        expenseResponse.setPaymentDetailId(savedPaymentDetails.getDetailId());
        return expenseResponse;
    }

    @Override
    public List<ExpenseResponse> getAllExpenses() {
        return expenseRepository.findAll().stream().map(expense -> {
            ExpenseResponse response = expenseMapper.toExpenseResponse(expense);
            response.setPaymentMethod(expense.getPaymentMethod());
            response.setBankName(expense.getBankName());
            response.setChequeDate(expense.getChequeDate());
            response.setChequeNumber(expense.getChequeNumber());
            return response;
        }).toList();
    }
}
