package com.example.smartPos.controllers.requests;

import lombok.Data;

import java.util.List;

@Data
public class SalesReturnRequest {
    private Integer saleId;

    private String invoiceNumber;

    private String customerName;

    private Integer customerId;

    private List<ReturnItems> returns;


    public Integer getSaleId() {
        return saleId;
    }

    public void setSaleId(Integer saleId) {
        this.saleId = saleId;
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

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }
}
