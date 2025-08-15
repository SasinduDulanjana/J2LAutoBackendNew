package com.example.smartPos.controllers.requests;

import com.example.smartPos.repositories.model.SaleProduct;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SaleRequest {

    private Integer saleId;
    private Integer orderId;
    private Integer custId;
    private Integer userId;
    private Date orderDate;
    private Double totalAmount;

    private Double subTotal;

    private String paymentType;
    private Double billWiseDiscountPercentage;

    private Double billWiseDiscountTotalAmount;

    private Double lineWiseDiscountTotalAmount;
    private String invoiceNumber;

    private List<SaleProduct> soldProducts;

    private Integer status;

    @JsonProperty("isFullyPaid")
    private Boolean isFullyPaid;

    @JsonProperty("isHold")
    private Boolean isHold;

    private Double paidAmount;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getCustId() {
        return custId;
    }

    public void setCustId(Integer custId) {
        this.custId = custId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public List<SaleProduct> getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(List<SaleProduct> soldProducts) {
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
}
