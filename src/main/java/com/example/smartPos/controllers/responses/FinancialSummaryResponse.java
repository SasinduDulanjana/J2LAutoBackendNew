package com.example.smartPos.controllers.responses;

import com.example.smartPos.repositories.model.Product;
import com.example.smartPos.repositories.model.Sale;
import lombok.Data;

@Data
public class FinancialSummaryResponse {
    private Double totalPurchases;
    private Double totalSales;
    private Double totalDiscounts;
    private Double dueAmountToPay;
    private Double dueAmountToReceive;

    private Double netProfit;

    public Double getTotalPurchases() {
        return totalPurchases;
    }

    public void setTotalPurchases(Double totalPurchases) {
        this.totalPurchases = totalPurchases;
    }

    public Double getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(Double totalSales) {
        this.totalSales = totalSales;
    }

    public Double getTotalDiscounts() {
        return totalDiscounts;
    }

    public void setTotalDiscounts(Double totalDiscounts) {
        this.totalDiscounts = totalDiscounts;
    }

    public Double getDueAmountToPay() {
        return dueAmountToPay;
    }

    public void setDueAmountToPay(Double dueAmountToPay) {
        this.dueAmountToPay = dueAmountToPay;
    }

    public Double getDueAmountToReceive() {
        return dueAmountToReceive;
    }

    public void setDueAmountToReceive(Double dueAmountToReceive) {
        this.dueAmountToReceive = dueAmountToReceive;
    }

    public Double getNetProfit() {
        return netProfit;
    }

    public void setNetProfit(Double netProfit) {
        this.netProfit = netProfit;
    }
}
