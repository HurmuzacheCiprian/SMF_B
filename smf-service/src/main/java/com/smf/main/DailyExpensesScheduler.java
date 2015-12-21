package com.smf.main;

import com.smf.main.domain.EconomyDao;
import com.smf.main.domain.ExpensesDao;
import com.smf.main.domain.FundDao;
import com.smf.main.domain.UserDao;
import com.smf.main.entities.Economy;
import com.smf.main.entities.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
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
    @Scheduled(fixedRate = 10000)
    public void computeDailyExpensesForAllUsers() {
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);

        if (yesterday.getDayOfMonth() == 1) {
            logger.info("A new month has started. Skipping the first day of the month.");
            return;
        }

        logger.info("Starting to compute the daily expenses.");
        List<String> eligibleUsers = userDao.getUsersWithNoEconomyForPreviousDay(yesterday.getDayOfMonth());


        logger.info("Found {} users to compute the daily expenses ", eligibleUsers.size());
        for (String userB : eligibleUsers) {
            UserEntity userEntity = userDao.findByUserName(userB);
            Long userExpenses = expensesDao.findTotalAmountDailyExpenses(userEntity.getId(), yesterday.getDayOfMonth());
            Long totalFunds = fundDao.findTotalAmountForUser(userEntity.getId());

            if (userExpenses == null) {
                logger.info("The user {} has no expenses registered yesterday. The daily expense computation is not possible.", userEntity.getId());
                continue;
            }
            if (totalFunds == null) {
                logger.info("The user {} has no funds registered. The daily expense computation is not possible.", userEntity.getId());
                continue;
            }

            Economy economy = new Economy();
            economy.setUserEntity(userEntity);
            economy.setAmount(totalFunds - userExpenses);
            economy.setCreatedDate(new Date());
            economy.setDailyEconomy(true);
            economy.setPrevDay(yesterday.getDayOfMonth());

            logger.info("Found daily economy with amount {} for day {}", (totalFunds - userExpenses), yesterday.getDayOfMonth());
            try {
                economyDao.save(economy);
            } catch (Exception ex) {
                logger.error("Error while saving the daily economy for user {}", userEntity.getId());
            }

        }

    }


}
