package com.smf.main.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by cipriach on 22.12.2015.
 */
@Builder
@Getter
@Setter
public class ExpenseReportResponse {
    private List<ExpenseReport> expenseReports;
    private Double remainingFunds;
    private Double totalFunds;
}
