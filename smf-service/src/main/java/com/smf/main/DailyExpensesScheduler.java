package com.smf.main;

import com.smf.main.domain.EconomyDao;
import com.smf.main.domain.ExpensesDao;
import com.smf.main.domain.FundDao;
import com.smf.main.domain.UserDao;
import com.smf.main.entities.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

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


    /**
     * This scheduler will compute all the daily expenses for all users.
     * If the totalFunds - totalExpenses is < 0 then the current economy is not an economy anymore and should have different storage of the economy like a field
     * <p>
     * 24h -> 86400000 ms
     */
    @Scheduled(fixedRate = 1 * 3600 * 1000)
    public void computeHourlyExpensesForAllUsers() {
//        LocalDate today = LocalDate.now();
//        LocalDate yesterday = today.minusDays(1);
        LocalDateTime currentDateTime = LocalDateTime.now();

        int month = currentDateTime.getMonth().getValue();
        int day = currentDateTime.getDayOfMonth();

        logger.info("Computing the expenses for all users at {}", currentDateTime);
        List<UserEntity> users = userDao.findAll();

        for (UserEntity user : users) {
            Double totalAmountDailyExpenses = expensesDao.findTotalAmountDailyExpenses(user.getId(), month, day);
            if (totalAmountDailyExpenses == null) {
                logger.info("User with user name {} has no expense until {}", user.getUserName(), currentDateTime);
            } else {
                Double totalFunds = fundDao.findTotalAmountForUser(user.getId());

                if (totalFunds == null) {
                    logger.info("User with user name {} has no funds registered", user.getUserName());
                } else {
                    if(user.getActualFunds() != null) {
                        user.setActualFunds(user.getActualFunds() - totalAmountDailyExpenses);
                    } else {
                        user.setActualFunds(totalFunds - totalAmountDailyExpenses);
                    }
                    userDao.save(user);
                }
            }
        }

    }

    public void computeDailyExpense() {

    }


}
