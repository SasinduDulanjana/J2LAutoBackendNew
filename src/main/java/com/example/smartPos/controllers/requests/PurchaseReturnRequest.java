package com.example.smartPos.controllers.requests;

import lombok.Data;

import java.util.List;

@Data
public class PurchaseReturnRequest {
    private Integer purchaseId;

    private String invoiceNumber;

    private String supplierName;

    private List<ReturnItems> returns;


    public Integer getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Integer purchaseId) {
        this.purchaseId = purchaseId;
    }

    public List<ReturnItems> getReturns() {
        return returns;
    }

    public void setReturns(List<ReturnItems> returns) {
        this.returns = returns;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }
}
