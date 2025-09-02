package com.example.smartPos.controllers.responses;

import lombok.Data;

@Data
public class MonthlyFinancialSummaryResponse {

    public MonthlyFinancialSummaryResponse(String month, Double revenue, Double purchases, Double netProfit) {
        this.month = month;
        this.sales = revenue;
        this.purchases = purchases;
        this.netProfit = netProfit;
    }

    private String month;
    private Double sales;
    private Double purchases;
    private Double netProfit;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Double getSales() {
        return sales;
    }

    public void setSales(Double sales) {
        this.sales = sales;
    }

    public Double getPurchases() {
        return purchases;
    }

    public void setPurchases(Double purchases) {
        this.purchases = purchases;
    }

    public Double getNetProfit() {
        return netProfit;
    }

    public void setNetProfit(Double netProfit) {
        this.netProfit = netProfit;
    }
}
