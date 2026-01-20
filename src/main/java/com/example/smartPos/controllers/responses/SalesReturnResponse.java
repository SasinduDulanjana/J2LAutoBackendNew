package com.example.smartPos.controllers.responses;

import com.example.smartPos.controllers.requests.ReturnItems;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SalesReturnResponse {
    private Integer saleId;

    private String invoiceNumber;

    private String customerName;

    private String returnDate;

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

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }
}
