package com.example.smartPos.controllers.responses;

public class BatchDetails {
    private String batchNo;
    private Double qty;

    private Double cost;
    private Double retailPrice;

    private Double wholesalePrice;

    public BatchDetails(String batchNo, Double qty, Double cost, Double retailPrice, Double wholesalePrice) {
        this.batchNo = batchNo;
        this.qty = qty;
        this.cost = cost;
        this.retailPrice = retailPrice;
        this.wholesalePrice = wholesalePrice;
    }

    // Getters and setters
    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public Double getQty() {
        return qty;
    }

    public void setQty(Double qty) {
        this.qty = qty;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
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
}