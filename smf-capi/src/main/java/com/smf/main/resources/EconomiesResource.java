package com.smf.main.resources;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by cipriach on 21.12.2015.
 */
@RestController
@RequestMapping(path = "/api/economies")
public class EconomiesResource {

    @RequestMapping(path = "/{userName}", method = RequestMethod.GET)
    public void getAllEconomies(@PathVariable("userName") String userName) {

    }


}
