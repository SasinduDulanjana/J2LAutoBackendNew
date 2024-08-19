package com.example.smartPos.controllers.requests;

import com.example.smartPos.repositories.model.Product;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PurchaseRequest {
    private Integer purchaseId;

    private Integer supId;

    private String purchaseName;

    private String invoiceNumber;

    private String deliveryTime;

    private String invoiceDate;

    private String connectionStatus;

    private String paymentStatus;

    private String productType;

    private List<ProductRequest> products;

    private Integer status;


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

    public Integer getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Integer purchaseId) {
        this.purchaseId = purchaseId;
    }

    public Integer getSupId() {
        return supId;
    }

    public void setSupId(Integer supplierId) {
        this.supId = supplierId;
    }

    public String getPurchaseName() {
        return purchaseName;
    }

    public void setPurchaseName(String purchaseName) {
        this.purchaseName = purchaseName;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getConnectionStatus() {
        return connectionStatus;
    }

    public void setConnectionStatus(String connectionStatus) {
        this.connectionStatus = connectionStatus;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public List<ProductRequest> getProducts() {
        return products;
    }

    public void setProducts(List<ProductRequest> products) {
        this.products = products;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
