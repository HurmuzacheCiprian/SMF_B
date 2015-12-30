package com.smf.main;

import com.smf.main.common.LocalDateUtil;
import com.smf.main.domain.ExpensesDao;
import com.smf.main.domain.FundDao;
import com.smf.main.domain.UserDao;
import com.smf.main.entities.Expense;
import com.smf.main.entities.UserEntity;
import com.smf.main.model.PeriodicCategoryReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cipriach on 30.12.2015.
 */
@Service
public class PeriodicReportService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private FundDao fundDao;

    @Autowired
    private ExpensesDao expensesDao;


    public PeriodicCategoryReport createPeriodicCategoryReport(String userName, int period) {
        LocalDate nowDate = LocalDate.now();
        UserEntity user = userDao.findByUserName(userName);

        List<Double> remainingFunds = new ArrayList<>();
        List<Double> expensesAmount = new ArrayList<>();

        for (Integer monthValue : LocalDateUtil.getLastPeriodicMonthValues(nowDate, period)) {
            Double totalFunds = fundDao.findTotalAmountForUser(user.getId());    // the total funds should not change from month to month because otherwise the economies won't be relevant
            Double totalExpenses = findTotalExpensesForMonth(user.getId(), monthValue);

            remainingFunds.add(totalFunds - totalExpenses);
            expensesAmount.add(totalExpenses);
        }

        return createReport(period, nowDate, remainingFunds, expensesAmount);
    }

    private PeriodicCategoryReport createReport(int period, LocalDate nowDate,
                                                List<Double> remainingFunds,
                                                List<Double> expenses) {
        PeriodicCategoryReport report = PeriodicCategoryReport.builder().build();
        report.setMonths(LocalDateUtil.getLastPeriodicMonths(nowDate, period));
        report.setRemainingFunds(remainingFunds);
        report.setExpensesAmount(expenses);
        return report;
    }

    private Double findTotalExpensesForMonth(Long userId, int monthValue) {
        List<Expense> expenses = expensesDao.findExpensesByMonth(userId, monthValue);
        if (expenses == null || expenses.size() == 0) {
            return 0D;
        }
        Double monthlyExpense = expenses.stream().map(exp -> exp.getAmount()).reduce(0D, (x, y) -> x + y);
        return monthlyExpense;
    }

}
