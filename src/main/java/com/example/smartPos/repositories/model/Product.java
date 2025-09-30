package com.example.smartPos.repositories.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "PRODUCTS")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;

    @ManyToOne
    @JoinColumn(name = "cat_Id", referencedColumnName = "catId")
    private Category category;

    private String productName;

    private Boolean isBarCodeAvailable;

    private String barcode;

    private String sku;

    private String barCodeType;

    private String productType;

    private String productStatus;

    private String description;

    private Boolean isStockManagementEnable;

    private String lowQty;

    private Boolean isExpDateAvailable;

    private String expDate;

    private String taxGroup;

    private String taxType;

    private String imgUrl;

    private String batchNo;

//    private Double cost;

    private Double remainingQty;

    private Integer status;

    private String brandName;

    private String partNumber;

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


    @ManyToMany
    @JoinTable(
            name = "vehicles_of_product",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "vehicle_id")
    )
    private Set<Vehicle> vehicles = new HashSet<>();

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

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Boolean getBarCodeAvailable() {
        return isBarCodeAvailable;
    }

    public void setBarCodeAvailable(Boolean barCodeAvailable) {
        isBarCodeAvailable = barCodeAvailable;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getBarCodeType() {
        return barCodeType;
    }

    public void setBarCodeType(String barCodeType) {
        this.barCodeType = barCodeType;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(String productStatus) {
        this.productStatus = productStatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getStockManagementEnable() {
        return isStockManagementEnable;
    }

    public void setStockManagementEnable(Boolean stockManagementEnable) {
        isStockManagementEnable = stockManagementEnable;
    }


    public String getLowQty() {
        return lowQty;
    }

    public void setLowQty(String lowQty) {
        this.lowQty = lowQty;
    }

    public Boolean getExpDateAvailable() {
        return isExpDateAvailable;
    }

    public void setExpDateAvailable(Boolean expDateAvailable) {
        isExpDateAvailable = expDateAvailable;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public String getTaxGroup() {
        return taxGroup;
    }

    public void setTaxGroup(String taxGroup) {
        this.taxGroup = taxGroup;
    }

    public String getTaxType() {
        return taxType;
    }

    public void setTaxType(String taxType) {
        this.taxType = taxType;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Double getRemainingQty() {
        return remainingQty;
    }

    public void setRemainingQty(Double remainingQty) {
        this.remainingQty = remainingQty;
    }

    public Set<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(Set<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }
}
