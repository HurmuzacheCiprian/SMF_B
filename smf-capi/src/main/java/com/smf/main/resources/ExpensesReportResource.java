package com.smf.main.resources;

import com.smf.main.ExpenseReportService;
import com.smf.main.model.CategoryReportResponse;
import com.smf.main.model.ExpenseReportResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    public CategoryReportResponse getCategoryExpenseReport(@PathVariable ("userName") String userName){
        return expenseReportService.getMonthlyReportPerCategory(userName);
    }


}
