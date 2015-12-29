package com.smf.main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smf.main.domain.ExpenseReportHistoryDao;
import com.smf.main.domain.ExpensesDao;
import com.smf.main.domain.FundDao;
import com.smf.main.domain.UserDao;
import com.smf.main.entities.Expense;
import com.smf.main.entities.ExpenseReportHistory;
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

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
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

    @Autowired
    private ExpenseReportHistoryDao expenseReportHistoryDao;

    private final ObjectMapper objectMapper = new ObjectMapper();


    public ExpenseReportResponse getDailyExpenseReport(String userName) {
        LocalDateTime localDateTime = LocalDateTime.now();
        UserEntity userEntity = userDao.findByUserName(userName);
        ExpenseReportResponse expenseReportResponse = new ExpenseReportResponse();

        if (userEntity == null) {
            logger.debug("No user found with user name {}", userName);
            return expenseReportResponse;
        }

        Double totalFundsAmount = fundDao.findTotalAmountForUser(userEntity.getId());

        List<Expense> expenses = expensesDao.findExpensesByDayAndMonth(userEntity.getId(), localDateTime.getDayOfMonth(), localDateTime.getMonthValue());

        if (expenses.size() == 0) {
            logger.debug("No expenses found for user {}", userName);
            expenseReportResponse.setExpenseReports(new ArrayList<>());
            expenseReportResponse.setRemainingFunds(userEntity.getActualFunds());
            return expenseReportResponse;
        } else {
            List<ExpenseReport> expenseReports = getExpenseReports(expenses);
            Long totalAmount = expenses.stream().map(e -> e.getAmount()).reduce(0L, (x, y) -> x + y);

            logger.debug("Found {} expenses {}", expenseReports.size(), expenseReports);
            expenseReportResponse.setExpenseReports(expenseReports);
            expenseReportResponse.setRemainingFunds(userEntity.getActualFunds());
            expenseReportResponse.setCurrentReportDate(localDateTime.getYear() + "-" + localDateTime.getMonthValue() + "-" + localDateTime.getDayOfMonth());
            expenseReportResponse.setTotalFunds(totalFundsAmount);
            expenseReportResponse.setLocalDateTime(localDateTime.toLocalDate().toString());
            return expenseReportResponse;
        }
    }

    public CategoryReportResponse getMonthlyReportPerCategory(String userName) {
        UserEntity user = userDao.findByUserName(userName);
        LocalDateTime localDateTime = LocalDateTime.now();
        Double totalFunds = fundDao.findTotalAmountForUser(user.getId());
        List<Object[]> categoryReport = expensesDao.findMonthlyCategoryReport(user.getId(), localDateTime.getMonthValue());

        Double remainingFunds = 0D;
        List<CategoryReport> categoryReportList = new ArrayList<>();
        for (Object[] obj : categoryReport) {
            Category category = Category.valueOf((String) obj[0]);
            BigDecimal bd = (BigDecimal) obj[1];
            Double amount = bd.doubleValue();
            remainingFunds += amount;
            Double percentage = ((amount * 100) / totalFunds);
            categoryReportList.add(CategoryReport.builder().category(category).percentage(String.format("%.3f", percentage) + "%").totalAmount(amount).build());
        }
        return CategoryReportResponse.builder().categoryReportList(categoryReportList).totalFunds(totalFunds).remainingFunds(totalFunds - remainingFunds).build();
    }

    public ExpenseReportResponse getHistoryReport(String userName, int year, int month, int dayOfMonth) throws IOException {
        LocalDate localDate = LocalDate.of(year, month, dayOfMonth);
        UserEntity userEntity = userDao.findByUserName(userName);
        if (userEntity != null) {
            ExpenseReportHistory historyReport = expenseReportHistoryDao.findByLocalDateAndUser(localDate, userEntity);

            if (historyReport != null) {
                return objectMapper.readValue(historyReport.getReport(), ExpenseReportResponse.class);
            }
            return null;
        }

        return null;

    }

    private List<ExpenseReport> getExpenseReports(List<Expense> expenses) {
        return expenses
                .stream()
                .map(expenseReport -> {
                    ExpenseReport expenseReport1 = new ExpenseReport();

                    expenseReport1.setCategory(expenseReport.getCategory());
                    expenseReport1.setDate(LocalDateTime.ofInstant(Instant.ofEpochMilli(expenseReport.getDate().getTime()), ZoneId.systemDefault()).toLocalDate().toString());
                    expenseReport1.setExpenseAmount(expenseReport.getAmount());
                    expenseReport1.setExpenseName(expenseReport.getExpenseName());

                    return expenseReport1;
                })
                .collect(Collectors.toCollection(ArrayList::new));
    }

}
