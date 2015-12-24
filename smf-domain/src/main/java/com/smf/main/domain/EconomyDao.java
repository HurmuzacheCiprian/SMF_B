package com.smf.main.domain;

import com.smf.main.entities.Economy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by cipriach on 21.12.2015.
 */

public interface EconomyDao extends JpaRepository<Economy, Long> {

    @Query(value = "select e from Economy e where e.user_entity_id = ?1 and e.prev_day = ?2", nativeQuery = true)
    List<Economy> findDailyEconomies(Long userId, int date);

}
