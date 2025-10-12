package com.example.smartPos.services.impl;

import com.example.smartPos.controllers.requests.ExpensePaymentUpdateRequest;
import com.example.smartPos.controllers.requests.ExpenseRequest;
import com.example.smartPos.controllers.responses.ChequeDetailsResponse;
import com.example.smartPos.controllers.responses.ExpenseResponse;
import com.example.smartPos.exception.ResourceNotFoundException;
import com.example.smartPos.mapper.ExpenseMapper;
import com.example.smartPos.repositories.ExpenseRepository;
import com.example.smartPos.repositories.PaymentDetailsRepository;
import com.example.smartPos.repositories.PaymentRepository;
import com.example.smartPos.repositories.model.Expense;
import com.example.smartPos.repositories.model.Payment;
import com.example.smartPos.repositories.model.PaymentDetails;
import com.example.smartPos.services.IPaymentService;
import com.example.smartPos.util.ErrorCodes;
import com.example.smartPos.util.PaymentStatus;
import com.example.smartPos.util.PaymentType;
import com.example.smartPos.util.ReferenceType;
import com.twilio.twiml.voice.Sim;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
        PaymentDetails savedPaymentDetails = null;
        if (request.getPaidAmount() != null || request.getPaidAmount() > 0) {
            PaymentDetails paymentDetails = new PaymentDetails();
            paymentDetails.setPayment(savedPayment);
            paymentDetails.setAmount(request.getPaidAmount());
            paymentDetails.setPaymentMethod(request.getPaymentType());
            paymentDetails.setBankName(request.getBankName());
            paymentDetails.setChequeNo(request.getChequeNumber());
            paymentDetails.setChequeDate(request.getChequeDate());
            if (request.getPaymentType().equalsIgnoreCase("CHEQUE")) {
                paymentDetails.setPaymentStatus(PaymentStatus.PENDING);
            } else {
                paymentDetails.setPaymentStatus(PaymentStatus.CLEARED);
            }
            paymentDetails.setPaymentDate(new Date());
            paymentDetails.fillNew(currentUser);
            savedPaymentDetails = paymentDetailsRepository.save(paymentDetails);
        }

        ExpenseResponse expenseResponse = new ExpenseResponse();
        expenseResponse.setId(savedExpense.getId());
        expenseResponse.setPaymentId(savedPayment.getPaymentId());
        if (savedPaymentDetails != null) {
            expenseResponse.setPaymentDetailId(savedPaymentDetails.getDetailId());
        } else {
            expenseResponse.setPaymentDetailId(null);
        }
        return expenseResponse;
    }

    @Override
    @Transactional
    public ExpenseResponse updateExpensePaymentAmount(ExpensePaymentUpdateRequest request) {

        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<Payment> payment = paymentRepository.findByReferenceIdAndPaymentPaymentTypeAndExpensesReferenceType(request.getExpenseId());
        if (payment.isEmpty()) {
            throw new ResourceNotFoundException(ErrorCodes.PAYMENT_NOT_FOUND);
        }
        PaymentDetails savedPaymentDetails = null;
        if (request.getAmount() != null || request.getAmount() > 0) {
            PaymentDetails paymentDetails = new PaymentDetails();
            paymentDetails.setPayment(payment.get());
            paymentDetails.setAmount(request.getAmount());
            paymentDetails.setPaymentMethod(request.getPaymentMethod());
            paymentDetails.setBankName(request.getBankName());
            paymentDetails.setChequeNo(request.getChequeNo());
            paymentDetails.setChequeDate(request.getChequeDate());
            if (request.getPaymentMethod().equalsIgnoreCase("CHEQUE")) {
                paymentDetails.setPaymentStatus(PaymentStatus.PENDING);
            } else {
                paymentDetails.setPaymentStatus(PaymentStatus.CLEARED);
            }
            paymentDetails.setPaymentDate(new Date());
            paymentDetails.fillNew(currentUser);
            savedPaymentDetails = paymentDetailsRepository.save(paymentDetails);
        }

        ExpenseResponse expenseResponse = new ExpenseResponse();
        expenseResponse.setPaymentId(payment.get().getPaymentId());
        if (savedPaymentDetails != null) {
            expenseResponse.setPaymentDetailId(savedPaymentDetails.getDetailId());
        } else {
            expenseResponse.setPaymentDetailId(null);
        }
        return expenseResponse;
    }

    @Override
    public List<ExpenseResponse> getAllExpenses() {
        SimpleDateFormat sm = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return expenseRepository.findAll().stream().map(expense -> {
            ExpenseResponse response = expenseMapper.toExpenseResponse(expense);
            response.setDate(sm.format(expense.getDate()));
            // Fetch payment details based on the expense ID
            Optional<Payment> payment = paymentRepository.findByReferenceIdAndPaymentPaymentTypeAndExpensesReferenceType(expense.getId().toString());
            if (payment.isPresent()) {
                // Set payment-related details in the response
                response.setPaymentId(payment.get().getPaymentId());
                response.setPaidAmount(paymentDetailsRepository.findTotalPaidAmountByPaymentId(payment.get().getPaymentId()));
            } else {
                response.setPaidAmount(0.0);
            }

            // Calculate due amount
            response.setDueAmount(expense.getAmount() - response.getPaidAmount());
            return response;
        }).toList();
    }

    @Override
    public List<PaymentDetails>  paymentDetailsOfExpenses(Integer expenseId) {
        List<PaymentDetails> paymentDetailsList = expenseRepository.findPaymentDetailsByExpenseId(expenseId);
        return paymentDetailsList;
    }
}
