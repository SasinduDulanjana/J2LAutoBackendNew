package com.example.smartPos.controllers.responses;

import com.example.smartPos.repositories.model.Customer;
import com.example.smartPos.repositories.model.User;
import com.example.smartPos.repositories.model.Vehicle;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SaleResponse extends CommonResponse {
    private Integer saleId;

    private Customer customer;

    private User user;

    private String saleDate;

    private Double totalAmount;

    private Double subTotal;

    private String paymentMethod;

    private Double billWiseDiscountPercentage;

    private Double billWiseDiscountTotalAmount;

    private Double lineWiseDiscountTotalAmount;

    private String invoiceNumber;

    private List<SoldProductResponse> soldProducts;

    private Integer status;

    private Boolean isFullyPaid;

    private Double paidAmount;

    private Boolean isHold;

    private String addBy;

    private String modifiedBy;

    private Date addedDate;

    private String modifiedDate;

    private Double outstandingBalance;

    private Vehicle vehicle;

    private String vehicleNumber;
    public Boolean getFullyPaid() {
        return isFullyPaid;
    }

    public void setFullyPaid(Boolean fullyPaid) {
        isFullyPaid = fullyPaid;
    }

    public Boolean getHold() {
        return isHold;
    }

    public void setHold(Boolean hold) {
        isHold = hold;
    }

    public String getAddBy() {
        return addBy;
    }

    public void setAddBy(String addBy) {
        this.addBy = addBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Date getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Integer getSaleId() {
        return saleId;
    }

    public void setSaleId(Integer saleId) {
        this.saleId = saleId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public List<SoldProductResponse> getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(List<SoldProductResponse> soldProducts) {
        this.soldProducts = soldProducts;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(Double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Double getOutstandingBalance() {
        return outstandingBalance;
    }

    public void setOutstandingBalance(Double outstandingBalance) {
        this.outstandingBalance = outstandingBalance;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }
}
