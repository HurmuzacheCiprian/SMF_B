package com.smf.main;

import com.smf.main.domain.UserDao;
import com.smf.main.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.smf.main.model.UserRegistration;
import com.smf.main.model.User;

import java.util.Date;

/**
 * Created by cipriach on 07.12.2015.
 */
@Service
public class CredentialsService {

    @Autowired
    private UserDao userDao;

    public boolean registerUser(UserRegistration userRegistration) {
        UserEntity entity = new UserEntity();
        entity.setCreatedDate(new Date());
        entity.setUserName(userRegistration.getUserName());
        entity.setFirstName(userRegistration.getFirstName());
        entity.setLastName(userRegistration.getLastName());
        entity.setPassword(userRegistration.getPassword());

        try {
            userDao.save(entity);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    public boolean checkUser(User user) {
        UserEntity foundUser = userDao.findByUserNameAndPassword(user.getUserName(), user.getPassword());

        return foundUser == null ? false : true;
    }


}
