package com.smf.main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smf.main.domain.ExpensesDao;
import com.smf.main.domain.FundDao;
import com.smf.main.domain.UserDao;
import com.smf.main.entities.Expense;
import com.smf.main.entities.Fund;
import com.smf.main.entities.UserEntity;
import com.smf.main.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by cipriach on 08.12.2015.
 */
@Service
public class SmfService {

    private final FundDao fundDao;
    private final UserDao userDao;
    private final ExpensesDao expenseDao;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public SmfService(FundDao fundDao, UserDao userDao, ExpensesDao expenseDao) {
        this.fundDao = fundDao;
        this.userDao = userDao;
        this.expenseDao = expenseDao;
    }

    public List<FundResponse> getAllFundsByUserName(final String userName) {
        Set<Fund> fundsResponse = userDao.findAllFundsByUserName(userName); //other method -> load the user by name and return its funds.

        return fundsResponse
                .stream()
                .map(fund -> FundResponse.builder()
                        .id(fund.getId())
                        .fundName(fund.getName())
                        .createdDate(LocalDateTime.ofInstant(Instant.ofEpochMilli(fund.getCreatedDate().getTime()), ZoneId.systemDefault()).toLocalDate().toString())
                        .amount(fund.getAmount())
                        .build())
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public List<FundResponse> getAllPageableFunds(int pageNumber, int perPage, String sortDirection, String sortField, String userName) {
        UserEntity userEntity = userDao.findByUserName(userName);
        if (userEntity == null) {
            return new ArrayList<>();
        }
        Sort.Direction sort = "asc".equals(sortDirection) ? Sort.Direction.ASC : Sort.Direction.DESC;
        List<Fund> fundsResponse = fundDao.findByuserEntity(userEntity, new PageRequest(pageNumber - 1, perPage, sort, sortField));
        return fundsResponse
                .stream()
                .map(fund -> FundResponse.builder()
                        .id(fund.getId())
                        .fundName(fund.getName())
                        .createdDate(LocalDateTime.ofInstant(Instant.ofEpochMilli(fund.getCreatedDate().getTime()), ZoneId.systemDefault()).toLocalDate().toString())
                        .amount(fund.getAmount())
                        .build())
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public int getTotalFunds(String userName) {
        return userDao.findAllFundsByUserName(userName).size();
    }

    @Transactional
    public boolean registerFund(String userName, FundRegistration fundRegistration) {
        UserEntity user = userDao.findByUserName(userName);
        if (user == null) {
            return false;
        }
        Fund fund = new Fund();
        fund.setCreatedDate(new Date());
        fund.setAmount(fundRegistration.getFundAmount());
        fund.setName(fundRegistration.getFundName());
        fund.setUserEntity(user);
        fundDao.save(fund);
        return true;
    }

    public List<ExpensesResponse> getAllExpensesByUserName(String userName) {
        Set<Expense> expensesResponse = userDao.findAllExpensesByUserName(userName);
        return expensesResponse
                .stream()
                .map(expense -> ExpensesResponse.builder().amount(expense.getAmount()).category(expense.getCategory()).build())
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Transactional
    public boolean registerExpense(String userName, ExpensesRegistration economyRegistration) {
        UserEntity userEntity = userDao.findByUserName(userName);
        if (userEntity == null) {
            return false;
        }

        Expense expense = new Expense();
        expense.setDate(new Date());
        expense.setUserEntity(userEntity);
        expense.setAmount(economyRegistration.getAmount());
        expense.setCategory(economyRegistration.getCategory());
        expenseDao.save(expense);

        return true;
    }

    public List<ExpensesResponse> getAllExpensesByCategory(String userName, Category category) {
        List<Expense> categorisedExpenses = expenseDao.findByCategory(category);
        return categorisedExpenses
                .stream()
                .map(expense -> ExpensesResponse.builder().amount(expense.getAmount()).category(expense.getCategory()).build())
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public FrequentCategory getMostFrequentlyUsedCategory() {
        List<Object[]> response = expenseDao.findMostFrequentExpenseCategory();
        Object[] o = response.get(0);   //the most frequent category and o[1] is the value of count
        return FrequentCategory.builder().category(Category.valueOf(o[0].toString())).count(Long.valueOf(o[1].toString())).build();
    }

    @Transactional
    public boolean deleteFund(String userName, Long fundId) {
        Long id = fundDao.findFundByUser(fundId, userName);
        if (id == null) {
            return false;
        }
        fundDao.delete(id);
        return true;
    }

}
