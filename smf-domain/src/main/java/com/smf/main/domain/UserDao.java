package com.smf.main.domain;

import com.smf.main.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by cipriach on 07.12.2015.
 */
@Transactional
public interface UserDao extends CrudRepository<UserEntity, Long> {
    UserEntity findByUserNameAndPassword(String userName, String password);
}
