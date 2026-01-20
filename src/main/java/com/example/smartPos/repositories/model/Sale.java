package com.example.smartPos.repositories.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "SALE")
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SALE_ID")
    private Integer saleId;

    @ManyToOne
    @JoinColumn(name = "custId", referencedColumnName = "custId")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user;

    private Date saleDate;

    private Double totalAmount;

    private Double subTotal;

    private String paymentMethod;

    private Double billWiseDiscountPercentage;

    private Double billWiseDiscountTotalAmount;

    private Double lineWiseDiscountTotalAmount;
    private String invoiceNumber;

    private Integer status;

    private Boolean isFullyPaid;

    private Boolean isHold;

    private Double paidAmount;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL)
    private List<SaleProduct> saleProducts;

    @ManyToOne
    @JoinColumn(name = "vehicle_id", referencedColumnName = "id")
    private Vehicle vehicle;

    private String vehicleNumber;
    private String addBy;

    @Column(name = "MODIFIED_BY")
    private String modifiedBy;

    private Date addedDate;

    @Column(name = "MODIFIED_DATE")
    private Date modifiedDate;

    public void fillNew(String userId){
        this.addBy = userId;
        this.addedDate = new Date();
        this.modifiedBy = userId;
        this.modifiedDate = new Date();
    }

    public void fillUpdated(String userId){
        this.modifiedBy = userId;
        this.modifiedDate = new Date();
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

    public Date getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Date saleDate) {
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

    public List<SaleProduct> getSaleProducts() {
        return saleProducts;
    }

    public void setSaleProducts(List<SaleProduct> saleProducts) {
        this.saleProducts = saleProducts;
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

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
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
