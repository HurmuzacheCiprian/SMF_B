package com.smf.main.resources;

import com.smf.main.Category;
import com.smf.main.SmfService;
import com.smf.main.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Created by cipriach on 08.12.2015.
 */
@RestController
@RequestMapping(path = "/api")
public class SmfResource {

    private final SmfService smfService;

    @Autowired
    public SmfResource(SmfService smfService) {
        this.smfService = smfService;
    }

    @RequestMapping(path = "/{userName}/funds", produces = "application/json", method = RequestMethod.GET)
    public FundsResponse getAllFunds(@PathVariable String userName,
                                     @RequestParam("pageNumber") int pageNumber,
                                     @RequestParam("perPage") int perPage,
                                     @RequestParam("direction") String direction,
                                     @RequestParam("sortField") String sortField) {
        return FundsResponse.builder().funds(smfService.getAllPageableFunds(pageNumber, perPage, direction, sortField, userName)).totalElements(smfService.getTotalFunds(userName)).build();
    }

    @RequestMapping(path ="/{userName}/expenses", produces = "application/json", method = RequestMethod.GET)
    public ExpensesResponse getAllExpenses(@PathVariable String userName,
                                          @RequestParam("pageNumber") int pageNumber,
                                          @RequestParam("perPage") int perPage,
                                          @RequestParam("direction") String direction,
                                          @RequestParam("sortField") String sortField){
        return ExpensesResponse.builder().expenses(smfService.getAllPageableExpenses(pageNumber, perPage, direction, sortField, userName)).totalElements(smfService.getTotalExpenses(userName)).build();
    }

    /*@RequestMapping(path = "/{userName}/expenses/{category}", produces = "application/json", method = RequestMethod.GET)
    public ExpenseResponse getAllExpensesByCategory(@PathVariable("userName") String userName, @PathVariable("category") Category category) {
        return ExpensesResponse.builder().expense(smfService.getAllExpensesByCategory(userName, category)).build();
    }*/

    @RequestMapping(path = "/expenses/frequent", produces = "application/json", method = RequestMethod.GET)
    public FrequentCategory getMostFrequentlyExpenseByCategory() {
        return smfService.getMostFrequentlyUsedCategory();
    }


    @RequestMapping(path = "/{userName}/register/fund", method = RequestMethod.POST)
    public HttpStatus registerFund(@PathVariable("userName") String userName, @RequestBody FundRegistration fundRegistration) {
        return smfService.registerFund(userName, fundRegistration) == true ? HttpStatus.ACCEPTED : HttpStatus.BAD_REQUEST;
    }

    @RequestMapping(path = "/{userName}/register/expense", method = RequestMethod.POST)
    public HttpStatus registerExpense(@PathVariable("userName") String userName, @RequestBody ExpensesRegistration expensesRegistration) {
        return smfService.registerExpense(userName, expensesRegistration) == true ? HttpStatus.ACCEPTED : HttpStatus.BAD_REQUEST;
    }

    @RequestMapping(path = "/{userName}/fund/{fundId}", method = RequestMethod.DELETE)
    public HttpStatus deleteFund(@PathVariable("userName") String userName, @PathVariable("fundId") Long fundId) {
        return smfService.deleteFund(userName, fundId) == true ? HttpStatus.OK : HttpStatus.NOT_FOUND;
    }

    @RequestMapping(path = "/{userName}/expense/{fundId}", method = RequestMethod.DELETE)
    public HttpStatus deleteExpense(@PathVariable("userName") String userName, @PathVariable("fundId") Long fundId) {
        return smfService.deleteExpense(userName, fundId) == true ? HttpStatus.OK : HttpStatus.NOT_FOUND;
    }

}
