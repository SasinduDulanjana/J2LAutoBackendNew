package com.example.smartPos.controllers.responses;

import com.example.smartPos.controllers.requests.TransactionDetails;
import com.example.smartPos.util.PaymentStatus;

import java.util.List;

public class CustomerPaymentDetailsResponse extends CommonResponse {

    private Integer customerId;

    private String customerName;

    private double totalSales;

    private double totalReturns;

    private double totalPayments;

    private double outstanding;

    private List<PaymentDetailsResponse> payments;

    private List<SalesReturnResponse> saleReturns;

    List<TransactionDetails> transactionDetails;

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public double getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(double totalSales) {
        this.totalSales = totalSales;
    }

    public double getTotalReturns() {
        return totalReturns;
    }

    public void setTotalReturns(double totalReturns) {
        this.totalReturns = totalReturns;
    }

    public double getTotalPayments() {
        return totalPayments;
    }

    public void setTotalPayments(double totalPayments) {
        this.totalPayments = totalPayments;
    }

    public double getOutstanding() {
        return outstanding;
    }

    public void setOutstanding(double outstanding) {
        this.outstanding = outstanding;
    }

    public List<PaymentDetailsResponse> getPayments() {
        return payments;
    }

    public void setPayments(List<PaymentDetailsResponse> payments) {
        this.payments = payments;
    }

    public List<SalesReturnResponse> getSaleReturns() {
        return saleReturns;
    }

    public void setSaleReturns(List<SalesReturnResponse> saleReturns) {
        this.saleReturns = saleReturns;
    }

    public List<TransactionDetails> getTransactionDetails() {
        return transactionDetails;
    }

    public void setTransactionDetails(List<TransactionDetails> transactionDetails) {
        this.transactionDetails = transactionDetails;
    }
}