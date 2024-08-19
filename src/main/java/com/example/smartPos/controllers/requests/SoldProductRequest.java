package com.example.smartPos.controllers.requests;

import lombok.Data;

@Data
public class SoldProductRequest {
    private Integer productId;
    private Integer batchId;
    private Integer qty;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getBatchId() {
        return batchId;
    }

    public void setBatchId(Integer batchId) {
        this.batchId = batchId;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }
}
