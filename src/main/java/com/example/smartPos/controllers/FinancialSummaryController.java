package com.example.smartPos.controllers;

import com.example.smartPos.controllers.responses.FinancialSummaryResponse;
import com.example.smartPos.controllers.responses.MonthlyFinancialSummaryResponse;
import com.example.smartPos.services.IFinancialSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/financialSummary")
public class FinancialSummaryController {

    @Autowired
    private IFinancialSummaryService financialSummaryService;

    @GetMapping("/totalSummary")
    public FinancialSummaryResponse getFinancialSummary() {
        return financialSummaryService.getFinancialSummary();
    }

    @GetMapping("/monthly")
    public List<MonthlyFinancialSummaryResponse> getMonthlyFinancialSummary() {
        return financialSummaryService.getMonthlyFinancialSummary();
    }
}
