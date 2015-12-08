package com.smf.main.resources;

import com.smf.main.Category;
import com.smf.main.SmfService;
import com.smf.main.model.ExpenseResponse;
import com.smf.main.model.ExpensesRegistration;
import com.smf.main.model.FundRegistration;
import com.smf.main.model.FundsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Created by cipriach on 08.12.2015.
 */
@RestController
@RequestMapping(path = "/api")
public class SmfResource {

    private SmfService smfService;

    @Autowired
    public SmfResource(SmfService smfService) {
        this.smfService = smfService;
    }

    @RequestMapping(path = "/{userName}/funds", produces = "application/json", method = RequestMethod.GET)
    public FundsResponse getAllFunds(@PathVariable String userName) {
        return FundsResponse.builder().funds(smfService.getAllFundsByUserName(userName)).build();
    }

    @RequestMapping(path = "/{userName}/expenses", produces = "application/json", method = RequestMethod.GET)
    public ExpenseResponse getAllExpenses(@PathVariable("userName") String userName) {
        return ExpenseResponse.builder().expense(smfService.getAllExpensesByUserName(userName)).build();
    }

    @RequestMapping(path = "/{userName}/expenses/{category}", produces = "application/json", method = RequestMethod.GET)
    public ExpenseResponse getAllExpensesByCategory(@PathVariable("userName") String userName, @PathVariable("category") Category category) {
        return ExpenseResponse.builder().expense(smfService.getAllExpensesByCategory(userName, category)).build();
    }


    @RequestMapping(path = "/{userName}/register/fund", method = RequestMethod.POST)
    public HttpStatus registerFund(@PathVariable("userName") String userName, @RequestBody FundRegistration fundRegistration) {
        return smfService.registerFund(userName, fundRegistration) == true ? HttpStatus.ACCEPTED : HttpStatus.BAD_REQUEST;
    }

    @RequestMapping(path = "/{userName}/register/expense", method = RequestMethod.POST)
    public HttpStatus registerExpense(@PathVariable("userName") String userName, @RequestBody ExpensesRegistration expensesRegistration) {
        return smfService.registerExpense(userName, expensesRegistration) == true ? HttpStatus.ACCEPTED : HttpStatus.BAD_REQUEST;
    }


}
