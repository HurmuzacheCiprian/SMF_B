package com.smf.main;

import com.smf.main.domain.ExpensesDao;
import com.smf.main.domain.FundDao;
import com.smf.main.domain.UserDao;
import com.smf.main.entities.Expense;
import com.smf.main.entities.UserEntity;
import com.smf.main.model.CategoryReport;
import com.smf.main.model.CategoryReportResponse;
import com.smf.main.model.ExpenseReport;
import com.smf.main.model.ExpenseReportResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by cipriach on 22.12.2015.
 */
@Service
@Transactional
public class ExpenseReportService {

    private static final Logger logger = LoggerFactory.getLogger(ExpenseReportService.class);

    @Autowired
    private ExpensesDao expensesDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private FundDao fundDao;


    public ExpenseReportResponse getDailyExpenseReport(String userName) {
        LocalDateTime localDateTime = LocalDateTime.now();
        UserEntity userEntity = userDao.findByUserName(userName);
        Double totalFundsAmount = fundDao.findTotalAmountForUser(userEntity.getId());

        if (userEntity == null) {
            logger.debug("No user found with user name {}", userName);
            return null;
        }

        if (userEntity.getActualFunds() != null) {
            totalFundsAmount = userEntity.getActualFunds();
        }

        List<Expense> expenses = expensesDao.findExpensesByDayAndMonth(userEntity.getId(), localDateTime.getDayOfMonth(), localDateTime.getMonthValue());
        if (expenses.size() == 0) {
            logger.debug("No expenses found for user {}", userName);
            return ExpenseReportResponse.builder().expenseReports(new ArrayList<>()).remainingFunds(totalFundsAmount).build();
        } else {
            List<ExpenseReport> expenseReports = expenses
                    .stream()
                    .map(expenseReport -> ExpenseReport.builder().category(expenseReport.getCategory()).date(LocalDateTime.ofInstant(Instant.ofEpochMilli(expenseReport.getDate().getTime()), ZoneId.systemDefault()).toLocalDate().toString()).expenseAmount(expenseReport.getAmount()).expenseName(expenseReport.getExpenseName()).build())
                    .collect(Collectors.toCollection(ArrayList::new));
            Long totalAmount = expenses.stream().map(e -> e.getAmount()).reduce(0L, (x, y) -> x + y);

            logger.debug("Found {} expenses {}", expenseReports.size(), expenseReports);
            return ExpenseReportResponse.builder()
                    .expenseReports(expenseReports).remainingFunds(totalFundsAmount - totalAmount).build();

        }

    }

    public CategoryReportResponse getMonthlyReportPerCategory(String userName) {
        UserEntity user = userDao.findByUserName(userName);
        LocalDateTime localDateTime = LocalDateTime.now();
        Double totalFunds = fundDao.findTotalAmountForUser(user.getId());
        Double remaningFunds = 0D;
        List<Object[]> categoryReport = expensesDao.findMonthlyCategoryReport(user.getId(), localDateTime.getMonthValue());
        List<CategoryReport> categoryReportList = new ArrayList<>();
        for (Object[] obj : categoryReport) {
            Category category = Category.valueOf((String) obj[0]);
            BigDecimal bd = (BigDecimal) obj[1];
            Double amount = bd.doubleValue();
            remaningFunds += amount;
            Double percentage = ((amount * 100) / totalFunds);
            categoryReportList.add(CategoryReport.builder().category(category).percentage(String.format("%.3f", percentage)+"%").totalAmount(amount).build());
        }
        return CategoryReportResponse.builder().categoryReportList(categoryReportList).totalFunds(totalFunds).remainingFunds(totalFunds - remaningFunds).build();
    }

}
