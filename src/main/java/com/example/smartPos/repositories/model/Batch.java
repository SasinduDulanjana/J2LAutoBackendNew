package com.example.smartPos.repositories.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "BATCH")
public class Batch {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer batchId;

    private String sku;

    private String batchNumber;

    private String price;

    private String supplier;

    private String purchaseDate;

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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
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
}
