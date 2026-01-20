package com.example.smartPos.controllers.requests;

import com.example.smartPos.controllers.responses.CommonResponse;
import com.example.smartPos.repositories.model.Batch;
import com.example.smartPos.repositories.model.Product;

public class ProductBatchRequest extends CommonResponse {

    private Integer purchaseId;

    private String batchNo;

    private Integer productId;

    public Integer getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Integer purchaseId) {
        this.purchaseId = purchaseId;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }
}
