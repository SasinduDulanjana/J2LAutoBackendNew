package com.example.smartPos.controllers.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SaleRequest {

    private Integer saleId;
    private Integer orderId;
    private CustomerRequest customer;
    private UserRequest user;
    private Date orderDate;
    private Double totalAmount;

    private Double subTotal;

    private String paymentType;
    private Double billWiseDiscountPercentage;

    private Double billWiseDiscountTotalAmount;

    private Double lineWiseDiscountTotalAmount;
    private String invoiceNumber;

    private List<SoldProductRequest> soldProducts;

    private Integer status;

    @JsonProperty("isFullyPaid")
    private Boolean isFullyPaid;

    @JsonProperty("isHold")
    private Boolean isHold;

    private Double paidAmount;

    private String chequeNumber;

    private String bankName;

    private String chequeDate;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public CustomerRequest getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerRequest customer) {
        this.customer = customer;
    }

    public UserRequest getUser() {
        return user;
    }

    public void setUser(UserRequest user) {
        this.user = user;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public List<SoldProductRequest> getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(List<SoldProductRequest> soldProducts) {
        this.soldProducts = soldProducts;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public Double getBillWiseDiscountPercentage() {
        return billWiseDiscountPercentage;
    }

    public void setBillWiseDiscountPercentage(Double billWiseDiscountPercentage) {
        this.billWiseDiscountPercentage = billWiseDiscountPercentage;
    }

    public Double getBillWiseDiscountTotalAmount() {
        return billWiseDiscountTotalAmount;
    }

    public void setBillWiseDiscountTotalAmount(Double billWiseDiscountTotalAmount) {
        this.billWiseDiscountTotalAmount = billWiseDiscountTotalAmount;
    }

    public Double getLineWiseDiscountTotalAmount() {
        return lineWiseDiscountTotalAmount;
    }

    public void setLineWiseDiscountTotalAmount(Double lineWiseDiscountTotalAmount) {
        this.lineWiseDiscountTotalAmount = lineWiseDiscountTotalAmount;
    }

    public Integer getStatus() {
        return status;
    }

    public Integer getSaleId() {
        return saleId;
    }

    public void setSaleId(Integer saleId) {
        this.saleId = saleId;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Boolean isFullyPaid() {
        return isFullyPaid;
    }

    public void setFullyPaid(Boolean fullyPaid) {
        isFullyPaid = fullyPaid;
    }

    public Double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(Double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public boolean isHold() {
        return isHold;
    }

    public void setHold(boolean hold) {
        isHold = hold;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentMethod) {
        this.paymentType = paymentMethod;
    }

    public Boolean getFullyPaid() {
        return isFullyPaid;
    }

    public Boolean getHold() {
        return isHold;
    }

    public void setHold(Boolean hold) {
        isHold = hold;
    }

    public String getChequeNumber() {
        return chequeNumber;
    }

    public void setChequeNumber(String chequeNumber) {
        this.chequeNumber = chequeNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getChequeDate() {
        return chequeDate;
    }

    public void setChequeDate(String chequeDate) {
        this.chequeDate = chequeDate;
    }
}
