package com.example.smartPos.services.impl;

import com.example.smartPos.controllers.responses.FinancialSummaryResponse;
import com.example.smartPos.controllers.responses.MonthlyFinancialSummaryResponse;
import com.example.smartPos.repositories.*;
import com.example.smartPos.services.IFinancialSummaryService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FinancialSummaryServiceImpl implements IFinancialSummaryService {

    private final PurchaseRepository purchaseRepository;

    private final SaleRepository saleRepository;

    private final PaymentDetailsRepository paymentDetailsRepository;

    private final ExpenseRepository expenseRepository;

    private final PurchaseReturnRepository purchaseReturnRepository;

    private final SalesReturnRepository salesReturnRepository;


    public FinancialSummaryServiceImpl(PurchaseRepository purchaseRepository, SaleRepository saleRepository, PaymentDetailsRepository paymentDetailsRepository, ExpenseRepository expenseRepository, PurchaseReturnRepository purchaseReturnRepository, SalesReturnRepository salesReturnRepository) {
        this.purchaseRepository = purchaseRepository;
        this.saleRepository = saleRepository;
        this.paymentDetailsRepository = paymentDetailsRepository;
        this.expenseRepository = expenseRepository;
        this.purchaseReturnRepository = purchaseReturnRepository;
        this.salesReturnRepository = salesReturnRepository;
    }


    @Override
    public FinancialSummaryResponse getFinancialSummary() {
        FinancialSummaryResponse response = new FinancialSummaryResponse();

        // Fetch data safely (defaulting to 0.0 if null)
        Double totalCogs = Optional.ofNullable(saleRepository.findTotalCOGS()).orElse(0.0);
        Double totalSales = Optional.ofNullable(saleRepository.findTotalSalesAmountForActiveSales()).orElse(0.0);
        Double totalExpenses = Optional.ofNullable(expenseRepository.findTotalExpenses()).orElse(0.0);
        Double totalPurchases = Optional.ofNullable(purchaseRepository.findTotalTotalCostForActivePurchases()).orElse(0.0);
        Double totalDiscounts = Optional.ofNullable(saleRepository.findTotalDiscountsForActiveSales()).orElse(0.0);
        Double totalPurchasePayments = Optional.ofNullable(paymentDetailsRepository.findTotalPurchasePayments()).orElse(0.0);
        Double totalSalePayments = Optional.ofNullable(paymentDetailsRepository.findTotalSalePayments()).orElse(0.0);

        // Calculate due amounts
        Double dueAmountToPay = totalPurchases - totalPurchasePayments;
        Double dueAmountToReceive = totalSales - totalSalePayments;

        // Fetch sales and purchase returns
        Double totalPurchaseReturns = Optional.ofNullable(purchaseReturnRepository.findTotalPurchaseReturns()).orElse(0.0);
        Double totalSalesReturns = Optional.ofNullable(salesReturnRepository.findTotalSalesReturns()).orElse(0.0);
        Double totalSalesReturnCogs = Optional.ofNullable(salesReturnRepository.findTotalSalesReturnCOGS()).orElse(0.0);

        // Adjust total sales and COGS with returns and discounts
        totalSales = totalSales - totalSalesReturns - totalDiscounts;
        totalCogs = totalCogs - totalPurchaseReturns - totalSalesReturnCogs;

        // Calculate net profit
        double netProfit = totalSales - totalCogs - totalExpenses;

        // Build response
        response.setTotalPurchases(totalPurchases);
        response.setTotalSales(totalSales);
        response.setTotalDiscounts(totalDiscounts);
        response.setTotalCogs(totalCogs);
        response.setTotalExpenses(totalExpenses);
        response.setDueAmountToPay(dueAmountToPay);
        response.setDueAmountToReceive(dueAmountToReceive);
        response.setNetProfit(netProfit);

        return response;
    }

    @Override
    public List<MonthlyFinancialSummaryResponse> getMonthlyFinancialSummary() {
        List<Object[]> revenueData = saleRepository.findMonthlyRevenue();
//        List<Object[]> purchaseData = purchaseRepository.findMonthlyPurchases();
        List<Object[]> cogsData = saleRepository.findMonthlyCOGS();
        List<Object[]> expenseData = expenseRepository.findMonthlyTotalExpenses();
        List<Object[]> purchaseReturnData = purchaseReturnRepository.findMonthlyPurchaseReturns();
        List<Object[]> saleReturnData = salesReturnRepository.findMonthlySaleReturns();

        Map<String, Double> revenueMap = revenueData.stream()
                .collect(Collectors.toMap(row -> (String) row[0], row -> (Double) row[1]));

//        Map<String, Double> purchaseMap = purchaseData.stream()
//                .collect(Collectors.toMap(row -> (String) row[0], row -> (Double) row[1]));

        Map<String, Double> cogsMap = cogsData.stream()
                .collect(Collectors.toMap(row -> (String) row[0], row -> (Double) row[1]));

        Map<String, Double> expenseMap = expenseData.stream()
                .collect(Collectors.toMap(row -> (String) row[0], row -> (Double) row[1]));

        Map<String, Double> purchaseReturnMap = purchaseReturnData.stream()
                .collect(Collectors.toMap(row -> (String) row[0], row -> (Double) row[1]));

        Map<String, Double> saleReturnMap = saleReturnData.stream()
                .collect(Collectors.toMap(row -> (String) row[0], row -> (Double) row[1]));


        Set<String> months = new HashSet<>();
        months.addAll(revenueMap.keySet());
//        months.addAll(purchaseMap.keySet());
        months.addAll(cogsMap.keySet());
        months.addAll(expenseMap.keySet());
        months.addAll(purchaseReturnMap.keySet());
        months.addAll(saleReturnMap.keySet());

        List<MonthlyFinancialSummaryResponse> summaries = new ArrayList<>();
        for (String month : months) {
            double revenue = revenueMap.getOrDefault(month, 0.0) - saleReturnMap.getOrDefault(month, 0.0);;
//            double purchases = purchaseMap.getOrDefault(month, 0.0);
            double cogs = cogsMap.getOrDefault(month, 0.0) -  purchaseReturnMap.getOrDefault(month, 0.0);;
            double expenses = expenseMap.getOrDefault(month, 0.0);
            double netProfit = revenue - cogs - expenses;

            summaries.add(new MonthlyFinancialSummaryResponse(month, revenue, cogs, netProfit, expenses));
        }

        return summaries;
    }
}
