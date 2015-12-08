package com.smf.main.resources;

import com.smf.main.SmfService;
import com.smf.main.model.FundRegistration;
import com.smf.main.model.FundResponse;
import com.smf.main.model.FundsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        List<FundResponse> response = smfService.getAllFundsByUserName(userName);
        return FundsResponse.builder().funds(response).build();
    }

    @RequestMapping(path = "/{userName}/register/fund", method = RequestMethod.POST)
    public HttpStatus registerFund(@PathVariable("userName") String userName, @RequestBody FundRegistration fundRegistration) {
        return smfService.registerFund(userName, fundRegistration) == true ? HttpStatus.ACCEPTED : HttpStatus.BAD_REQUEST;
    }

}
