package com.example.smartPos.repositories.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "BATCH")
public class Batch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer batchId;

    private String sku;

    private String batchNumber;

    private Double unitCost;

    private Double retailPrice;

    private Double qty;

    private Double wholesalePrice;


    private Integer supplier;

    private String purchaseDate;

    private String invoiceNumber;

    //    @NotNull
//    @Size(min = 1, max = 50)
//    @Column(name = "ADD_BY")
//    @ApiField(fieldName = "addBy")
    private String addBy;
    //    @Size(max = 50)
//    @Column(name = "MDFY_BY")
//    @ApiField(fieldName = "modifiedBy")
    private String modifiedBy;
    //    @NotNull
//    @Column(name = "ADD_DATE")
//    @ApiField(fieldName = "addDate")
//    @Temporal(TemporalType.TIMESTAMP)
    private Date addedDate;
    //    @Column(name = "MDFY_DATE")
//    @ApiField(fieldName = "modifiedDate")
//    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;

    public void fillNew(String userId) {
        this.addBy = userId;
        this.addedDate = new Date();
        this.modifiedBy = userId;
        this.modifiedDate = new Date();
    }

    public void fillUpdated(String userId) {
        this.modifiedBy = userId;
        this.modifiedDate = new Date();
    }

    public Integer getBatchId() {
        return batchId;
    }

    public void setBatchId(Integer batchId) {
        this.batchId = batchId;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public Integer getSupplier() {
        return supplier;
    }

    public void setSupplier(Integer supplier) {
        this.supplier = supplier;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
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

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Double getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(Double unitCost) {
        this.unitCost = unitCost;
    }

    public Double getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(Double retailPrice) {
        this.retailPrice = retailPrice;
    }

    public Double getWholesalePrice() {
        return wholesalePrice;
    }

    public void setWholesalePrice(Double wholesalePrice) {
        this.wholesalePrice = wholesalePrice;
    }

    public Double getQty() {
        return qty;
    }

    public void setQty(Double qty) {
        this.qty = qty;
    }

}

