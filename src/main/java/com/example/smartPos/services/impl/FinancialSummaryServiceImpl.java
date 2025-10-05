package com.example.smartPos.services.impl;

import com.example.smartPos.controllers.responses.FinancialSummaryResponse;
import com.example.smartPos.controllers.responses.MonthlyFinancialSummaryResponse;
import com.example.smartPos.repositories.PaymentDetailsRepository;
import com.example.smartPos.repositories.PurchaseRepository;
import com.example.smartPos.repositories.SaleRepository;
import com.example.smartPos.services.IFinancialSummaryService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FinancialSummaryServiceImpl implements IFinancialSummaryService {

    private final PurchaseRepository purchaseRepository;

    private final SaleRepository saleRepository;

    private final PaymentDetailsRepository paymentDetailsRepository;

    public FinancialSummaryServiceImpl(PurchaseRepository purchaseRepository, SaleRepository saleRepository, PaymentDetailsRepository paymentDetailsRepository) {
        this.purchaseRepository = purchaseRepository;
        this.saleRepository = saleRepository;
        this.paymentDetailsRepository = paymentDetailsRepository;
    }


    @Override
    public FinancialSummaryResponse getFinancialSummary() {
        FinancialSummaryResponse response = new FinancialSummaryResponse();

        // Example calculations (replace with actual database queries)
        Double totalCogs = saleRepository.findTotalCOGS();
        Double totalSales = saleRepository.findTotalSalesAmountForActiveSales();

        response.setTotalPurchases(purchaseRepository.findTotalTotalCostForActivePurchases()); // Fetch total purchases from the database
        response.setTotalSales(totalSales);    // Fetch total sales from the database
        response.setTotalDiscounts(saleRepository.findTotalDiscountsForActiveSales()); // Fetch total discounts from the database
        response.setTotalCogs(totalCogs); // Fetch total COGS from the database
        response.setDueAmountToPay(paymentDetailsRepository.findPurchaseOutstanding()); // Fetch due amount to pay from the database
        response.setDueAmountToReceive(paymentDetailsRepository.findSaleOutstanding()); // Fetch due amount to receive from the database
        response.setNetProfit(totalSales - totalCogs);
        return response;
    }

    @Override
    public List<MonthlyFinancialSummaryResponse> getMonthlyFinancialSummary() {
        List<Object[]> revenueData = saleRepository.findMonthlyRevenue();
//        List<Object[]> purchaseData = purchaseRepository.findMonthlyPurchases();
        List<Object[]> cogsData = saleRepository.findMonthlyCOGS();


        Map<String, Double> revenueMap = revenueData.stream()
                .collect(Collectors.toMap(row -> (String) row[0], row -> (Double) row[1]));

//        Map<String, Double> purchaseMap = purchaseData.stream()
//                .collect(Collectors.toMap(row -> (String) row[0], row -> (Double) row[1]));

        Map<String, Double> cogsMap = cogsData.stream()
                .collect(Collectors.toMap(row -> (String) row[0], row -> (Double) row[1]));

        Set<String> months = new HashSet<>();
        months.addAll(revenueMap.keySet());
//        months.addAll(purchaseMap.keySet());
        months.addAll(cogsMap.keySet());

        List<MonthlyFinancialSummaryResponse> summaries = new ArrayList<>();
        for (String month : months) {
            double revenue = revenueMap.getOrDefault(month, 0.0);
//            double purchases = purchaseMap.getOrDefault(month, 0.0);
            double cogs = cogsMap.getOrDefault(month, 0.0);
            double netProfit = revenue - cogs;

            summaries.add(new MonthlyFinancialSummaryResponse(month, revenue, cogs, netProfit));
        }

        return summaries;
    }
}
