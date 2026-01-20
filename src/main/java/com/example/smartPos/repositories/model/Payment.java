package com.example.smartPos.repositories.model;

import com.example.smartPos.util.PaymentType;
import com.example.smartPos.util.ReferenceType;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Integer paymentId;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type", nullable = false)
    private PaymentType paymentType; // RECEIPT or PAYMENT

    @Column(name = "reference_id", nullable = false)
    private String referenceId; // Links to sale_id or purchase_id

    @Enumerated(EnumType.STRING)
    @Column(name = "reference_type", nullable = false)
    private ReferenceType referenceType; // SALE or PURCHASE

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "custId", referencedColumnName = "custId", foreignKey = @ForeignKey(name = "fk_customer"))
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supId", referencedColumnName = "supId", foreignKey = @ForeignKey(name = "fk_supplier"))
    private Supplier supplier;

    @Column(name = "payment_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date paymentDate;

    @Column(name = "total_amount", nullable = false)
    private Double totalAmount;

    @Column(name = "remarks", length = 255)
    private String remarks;

    private String addBy;

    private String modifiedBy;

    private Date addedDate;

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

    // Getters and Setters
    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public ReferenceType getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(ReferenceType referenceType) {
        this.referenceType = referenceType;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
