package com.example.smartPos.controllers.responses;

import com.example.smartPos.controllers.requests.TransactionDetails;

import java.util.List;

public class SupplierPaymentDetailsResponse extends CommonResponse {

    private Integer supplierId;

    private String supplierName;

    private double totalPurchases;

    private double totalReturns;

    private double totalPayments;

    private double outstanding;

    private List<PaymentDetailsResponse> payments;

    private List<PurchaseReturnResponse> purchaseReturns;

    List<TransactionDetails> transactionDetails;

    public double getTotalPurchases() {
        return totalPurchases;
    }

    public void setTotalPurchases(double totalPurchases) {
        this.totalPurchases = totalPurchases;
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

    public List<TransactionDetails> getTransactionDetails() {
        return transactionDetails;
    }

    public void setTransactionDetails(List<TransactionDetails> transactionDetails) {
        this.transactionDetails = transactionDetails;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public List<PurchaseReturnResponse> getPurchaseReturns() {
        return purchaseReturns;
    }

    public void setPurchaseReturns(List<PurchaseReturnResponse> purchaseReturns) {
        this.purchaseReturns = purchaseReturns;
    }
}