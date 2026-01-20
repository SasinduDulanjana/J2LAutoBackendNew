package com.example.smartPos.controllers.responses;

import lombok.Data;

@Data
public class MonthlyFinancialSummaryResponse {

    public MonthlyFinancialSummaryResponse(String month, Double revenue, Double cogs, Double netProfit, Double expenses) {
        this.month = month;
        this.sales = revenue;
        this.cogs = cogs;
        this.netProfit = netProfit;
        this.expenses = expenses;
    }

    private String month;
    private Double sales;

    private Double expenses;
    private Double cogs;
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

    public Double getNetProfit() {
        return netProfit;
    }

    public void setNetProfit(Double netProfit) {
        this.netProfit = netProfit;
    }


    public Double getCogs() {
        return cogs;
    }
    public void setCogs(Double cogs) {
        this.cogs = cogs;
    }

    public Double getExpenses() {
        return expenses;
    }

    public void setExpenses(Double expenses) {
        this.expenses = expenses;
    }
}
