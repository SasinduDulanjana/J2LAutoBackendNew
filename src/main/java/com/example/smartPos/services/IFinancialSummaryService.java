package com.example.smartPos.services;

import com.example.smartPos.controllers.responses.FinancialSummaryResponse;
import com.example.smartPos.controllers.responses.MonthlyFinancialSummaryResponse;

import java.util.List;

public interface IFinancialSummaryService {

    FinancialSummaryResponse getFinancialSummary();

    List<MonthlyFinancialSummaryResponse> getMonthlyFinancialSummary();
}
