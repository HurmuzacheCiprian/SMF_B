package com.smf.main.domain;

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
    UserEntity findByUserNameAndPassword(String userName, String password);

    @Query("SELECT u.funds FROM UserEntity u WHERE u.userName = ?1")
    Set<Fund> findAllFundsByUserName(String userName);

    UserEntity findByUserName(String userName);
}
