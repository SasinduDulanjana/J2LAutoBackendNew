package com.example.smartPos.controllers.responses;

import com.example.smartPos.repositories.model.Vehicle;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ProductResponse extends CommonResponse {
    private Integer productId;

    private CategoryResponse category;

    private String productName;

    private Boolean isBarCodeAvailable;

    private String barCode;

    private String sku;

    private String barCodeType;

    private String productType;

    private String productStatus;

    private String description;

    private Boolean isStockManagementEnable;

    private String salePrice;

    private String wholeSalePrice;

    private String lowQty;

    private Boolean isExpDateAvailable;

    private String expDate;

    private String taxGroup;

    private String taxType;

    private String imgUrl;

    private String batchNo;

    private Integer status;

    private List<BatchDetails> batchQuantities;

    private  String brandName;

    private String partNumber;

    private List<Vehicle> vehicleList;

    private String addBy;

    private String modifiedBy;

    private Date addedDate;

    private Date modifiedDate;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public CategoryResponse getCategory() {
        return category;
    }

    public void setCategory(CategoryResponse category) {
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

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
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

    public String getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
    }

    public String getWholeSalePrice() {
        return wholeSalePrice;
    }

    public void setWholeSalePrice(String wholeSalePrice) {
        this.wholeSalePrice = wholeSalePrice;
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

    public List<BatchDetails> getBatchQuantities() {
        return batchQuantities;
    }

    public void setBatchQuantities(List<BatchDetails> batchQuantities) {
        this.batchQuantities = batchQuantities;
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

    public List<Vehicle> getVehicleList() {
        return vehicleList;
    }

    public void setVehicleList(List<Vehicle> vehicleList) {
        this.vehicleList = vehicleList;
    }
}
