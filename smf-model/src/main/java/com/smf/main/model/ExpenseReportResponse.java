package com.smf.main.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by cipriach on 22.12.2015.
 */
@Getter
@Setter
@NoArgsConstructor
public class ExpenseReportResponse {
    private List<ExpenseReport> expenseReports;
    private Double remainingFunds;
    private Double totalFunds;
    private String currentReportDate;
    private String localDateTime;
}
