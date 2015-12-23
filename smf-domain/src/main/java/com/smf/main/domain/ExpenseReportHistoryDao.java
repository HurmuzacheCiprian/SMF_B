package com.smf.main.domain;

import com.smf.main.entities.ExpenseReportHistory;
import com.smf.main.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

/**
 * Created by cipriach on 23.12.2015.
 */
@Transactional
public interface ExpenseReportHistoryDao extends JpaRepository<ExpenseReportHistory, Long> {

    ExpenseReportHistory findByLocalDate(LocalDate localDate);

    @Query("select eh from ExpenseReportHistory eh where userEntity = ?2 and localDate = ?1")
    ExpenseReportHistory findByLocalDateAndUser(LocalDate localDate, UserEntity userEntity);
}
