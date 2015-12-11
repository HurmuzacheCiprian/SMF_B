package com.smf.main.domain;

import com.smf.main.entities.Expense;
import com.smf.main.entities.Fund;
import com.smf.main.entities.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Created by cipriach on 07.12.2015.
 */
@Transactional
public interface UserDao extends CrudRepository<UserEntity, Long> {
    @Query("SELECT u from UserEntity u where u.userName = ?1 AND u.password = ?2")
    UserEntity findByUserNameAndPassword(String userName, String password);

    @Query("SELECT u.funds FROM UserEntity u WHERE u.userName = ?1")
    Set<Fund> findAllFundsByUserName(String userName);

    @Query("SELECT u.expenses FROM UserEntity u where u.userName = ?1")
    Set<Expense> findAllExpensesByUserName(String userName);

    UserEntity findByUserName(String userName);
}
