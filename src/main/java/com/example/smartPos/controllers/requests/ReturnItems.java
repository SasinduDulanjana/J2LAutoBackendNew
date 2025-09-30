package com.example.smartPos.controllers.requests;

import lombok.Data;

@Data
public class ReturnItems {
    private String productName;

    private Integer productId;

    private Integer quantityToReturn;

    private String condition;

    private String reason;

    private Integer refundAmount;

    private String batchNumber;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getQuantityToReturn() {
        return quantityToReturn;
    }

    public void setQuantityToReturn(Integer quantityToReturn) {
        this.quantityToReturn = quantityToReturn;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(Integer refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }
}
