package com.smf.main.resources;

import com.smf.main.EconomiesService;
import com.smf.main.model.EconomiesResponse;
import com.smf.main.model.EconomyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by cipriach on 21.12.2015.
 */
@RestController
@RequestMapping(path = "/api/economies")
public class EconomiesResource {

    @Autowired
    private EconomiesService economiesService;

    @RequestMapping(path = "/{userName}", method = RequestMethod.GET)
    public EconomiesResponse getAllEconomies(@PathVariable("userName") String userName,
                                             @RequestParam("pageNumber") int pageNumber,
                                             @RequestParam("perPage") int perPage,
                                             @RequestParam("direction") String direction,
                                             @RequestParam("sortField") String sortField) {

        return null;
    }

    @RequestMapping(path = "/{userName}/daily", method = RequestMethod.GET)
    public EconomiesResponse getDailyEconomies(@PathVariable("userName") String userName) {
        List<EconomyResponse> response = economiesService.getDailyEconomies(userName);
        return EconomiesResponse.builder().economies(response).totalElements(0).build();
    }

    @RequestMapping(path = "/{userName}/monthly", method = RequestMethod.GET)
    public EconomiesResponse getMonthlyEconomies(@PathVariable("userName") String userName) {
        return null;
    }


}
