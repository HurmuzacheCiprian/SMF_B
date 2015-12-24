package com.smf.main;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smf.main.domain.*;
import com.smf.main.entities.ExpenseReportHistory;
import com.smf.main.entities.UserEntity;
import com.smf.main.model.ExpenseReportResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by cipriach on 21.12.2015.
 */
@Service
public class DailyExpensesScheduler {

    private static final Logger logger = LoggerFactory.getLogger(DailyExpensesScheduler.class);

    @Autowired
    private EconomyDao economyDao;

    @Autowired
    private ExpensesDao expensesDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private FundDao fundDao;

    @Autowired
    private ExpenseReportService expenseReportService;

    @Autowired
    private ExpenseReportHistoryDao expenseReportHistoryDao;

    private final ObjectMapper objectMapper = new ObjectMapper();


    /**
     * This scheduler will compute all the daily expenses for all users.
     * If the totalFunds - totalExpenses is < 0 then the current economy is not an economy anymore and should have different storage of the economy like a field
     * <p>
     * 24h -> 86400000 ms
     */
    @Scheduled(fixedRate = 24 * 3600 * 1000)
    public void computeHourlyExpensesForAllUsers() {
        LocalDateTime currentDateTime = LocalDateTime.now();

        int month = currentDateTime.getMonth().getValue();
        int day = currentDateTime.getDayOfMonth();

        logger.info("Computing the expenses for all users at {}", currentDateTime);
        List<UserEntity> users = userDao.findAll();

        for (UserEntity user : users) {
            Double totalAmountDailyExpenses = expensesDao.findTotalAmountDailyExpenses(user.getId(), month, day);
            if (totalAmountDailyExpenses == null) {
                logger.info("User with user name {} has no expense today {}", user.getUserName(), currentDateTime);
            } else {
                computeActualFundsForUser(user, totalAmountDailyExpenses);
            }
        }

    }


    @Scheduled(fixedRate = 3 * 3600 * 1000)
    public void computeExpenseHistory() throws JsonProcessingException {
        logger.info("Computing the expense history");
        List<UserEntity> userEntities = userDao.findAll();
        for (UserEntity user : userEntities) {
            ExpenseReportResponse dailyReport = expenseReportService.getDailyExpenseReport(user.getUserName());

            if (dailyReport.getLocalDateTime() == null) {
                logger.info(String.format("No expenses registered today for user %s", user.getUserName()));
                continue;
            }
            ExpenseReportHistory expenseReportHistory = expenseReportHistoryDao.findByLocalDate(LocalDate.parse(dailyReport.getLocalDateTime()));

            if (dailyReport.getExpenseReports().isEmpty()) {
                logger.debug(String.format("The user %s has no expenses", user.getUserName()));
                continue;
            }
            setExpenseReportHistory(user, dailyReport, expenseReportHistory);
        }

    }

    private void setExpenseReportHistory(UserEntity user, ExpenseReportResponse dailyReport, ExpenseReportHistory expenseReportHistory) throws JsonProcessingException {
        if (expenseReportHistory == null) {
            logger.debug(String.format("No expense report history found for date %s", dailyReport.getLocalDateTime()));
            ExpenseReportHistory history = new ExpenseReportHistory();
            history.setUserEntity(user);
            history.setLocalDate(LocalDate.parse(dailyReport.getLocalDateTime()));
            history.setReport(objectMapper.writeValueAsString(dailyReport));
            expenseReportHistoryDao.save(history);
        } else {
            logger.debug(String.format("Expense report history already found for date %s", dailyReport.getLocalDateTime()));
            expenseReportHistory.setReport(objectMapper.writeValueAsString(dailyReport));
            expenseReportHistoryDao.save(expenseReportHistory);
        }
    }

    private void computeActualFundsForUser(UserEntity user, Double totalAmountDailyExpenses) {
        Double totalFunds = fundDao.findTotalAmountForUser(user.getId());
        if (totalFunds == null) {
            logger.info("User with user name {} has no funds registered", user.getUserName());
        } else {
            if (user.getActualFunds() != null) {
                user.setActualFunds(user.getActualFunds() - totalAmountDailyExpenses);
            } else {
                user.setActualFunds(totalFunds - totalAmountDailyExpenses);
            }
            userDao.save(user);
        }
    }


}
