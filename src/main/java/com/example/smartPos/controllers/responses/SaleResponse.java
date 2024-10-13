package com.example.smartPos.controllers.responses;

import com.example.smartPos.repositories.model.SaleProduct;
import lombok.Data;

import java.util.List;

@Data
public class SaleResponse extends CommonResponse {
    private Integer saleId;

    private Integer custId;

    private Integer userId;

    private String saleDate;

    private Double totalAmount;

    private Double subTotal;

    private Double billWiseDiscountPercentage;

    private Double billWiseDiscountTotalAmount;

    private Double lineWiseDiscountTotalAmount;

    private String invoiceNumber;

    private List<SaleProduct> soldProducts;

    public Integer getSaleId() {
        return saleId;
    }

    public void setSaleId(Integer saleId) {
        this.saleId = saleId;
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

    public String getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(String saleDate) {
        this.saleDate = saleDate;
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

    public List<SaleProduct> getProducts() {
        return soldProducts;
    }

    public void setProducts(List<SaleProduct> products) {
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

    public List<SaleProduct> getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(List<SaleProduct> soldProducts) {
        this.soldProducts = soldProducts;
    }
}
