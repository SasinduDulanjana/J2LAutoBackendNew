package com.example.smartPos.controllers.requests;

import com.example.smartPos.repositories.model.SaleProduct;
import lombok.Data;

import java.util.List;

@Data
public class SaleRequest {
    private Integer orderId;
    private Integer custId;
    private Integer userId;
    private String orderDate;
    private Double totalAmount;

    private Double subTotal;

    private Double billWiseDiscountPercentage;

    private Double billWiseDiscountTotalAmount;

    private Double lineWiseDiscountTotalAmount;
    private String invoiceNumber;

    private List<SaleProduct> soldProducts;

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

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
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
}
