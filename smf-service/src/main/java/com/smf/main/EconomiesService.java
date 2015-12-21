package com.smf.main;

import com.smf.main.domain.EconomyDao;
import com.smf.main.domain.UserDao;
import com.smf.main.entities.Economy;
import com.smf.main.entities.UserEntity;
import com.smf.main.model.EconomyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by cipriach on 21.12.2015.
 */
@Service
public class EconomiesService {

    @Autowired
    private EconomyDao economyDao;

    @Autowired
    private UserDao userDao;

    /**
     * This method will return all the daily economies.
     *
     * @param userName
     * @return
     */
    public List<EconomyResponse> getDailyEconomies(String userName) {
        UserEntity userEntity = userDao.findByUserName(userName);
        if (userEntity == null) {
            return new ArrayList<>();
        }
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);
        List<Economy> response = economyDao.findDailyEconomies(userEntity.getId(), yesterday.getDayOfMonth());
        return response
                .stream()
                .map(economy -> EconomyResponse.builder().amount(economy.getAmount()).economyDate(economy.getCreatedDate()).build())
                .collect(Collectors.toCollection(ArrayList::new));
    }

}
