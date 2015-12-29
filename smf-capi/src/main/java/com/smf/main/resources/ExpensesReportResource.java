package com.smf.main.resources;

import com.smf.main.ExpenseReportService;
import com.smf.main.model.CategoryReportResponse;
import com.smf.main.model.ExpenseReportResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * Created by cipriach on 22.12.2015.
 */
@RestController
@RequestMapping(path = "/api/expense/report")
public class ExpensesReportResource {

    @Autowired
    private ExpenseReportService expenseReportService;

    @RequestMapping(path = "/daily/{userName}", method = RequestMethod.GET)
    public ExpenseReportResponse getHourlyExpenseReport(@PathVariable("userName") String userName) {
        return expenseReportService.getDailyExpenseReport(userName);
    }

    @RequestMapping(path = "/monthly/{userName}/categories", method = RequestMethod.GET)
    public CategoryReportResponse getCategoryExpenseReport(@PathVariable("userName") String userName) {
        return expenseReportService.getMonthlyReportPerCategory(userName);
    }

    @RequestMapping(path = "/history/{userName}/categories", method = RequestMethod.GET)
    public ExpenseReportResponse getHistoryCategoryExpenseReport(@PathVariable("userName") String userName,
                                                                 @RequestParam("month") int month,
                                                                 @RequestParam("day") int dayOfMonth,
                                                                 @RequestParam("year") int year) {
        try {
            return expenseReportService.getHistoryReport(userName, year, month, dayOfMonth);
        } catch (IOException e) {
            return new ExpenseReportResponse();
        }
    }

    //TODO
    @RequestMapping(path = "/daily/{userName}/category", method = RequestMethod.GET)
    public ExpenseReportResponse getDailyExpenseReportCategory(@PathVariable("userName") String userName) {
        return expenseReportService.getDailyExpenseReport(userName);
    }
}
