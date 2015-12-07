package com.smf.main.resources;

import com.smf.main.domain.UserDao;
import com.smf.main.entities.UserEntity;
import com.smf.main.model.User;
import com.smf.main.model.UserRegistration;
import com.smf.main.model.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * Created by cipriach on 07.12.2015.
 */
@RestController
public class CredentialsResource {

    @Autowired
    private UserDao userDao;

    @RequestMapping(path = "/login", method = RequestMethod.POST, produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<UserResponse> checkLoginCredentials(@RequestBody User user) {
        UserEntity foundUser = userDao.findByUserNameAndPassword(user.getUserName(), user.getPassword());
        if (foundUser != null) {
            return new ResponseEntity(UserResponse.builder().isOk(true).build(), HttpStatus.OK);
        }

        return new ResponseEntity(UserResponse.builder().isOk(false).build(), HttpStatus.NOT_FOUND);

    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public HttpStatus registerUser(@RequestBody UserRegistration user) {
        UserEntity entity = new UserEntity();
        entity.setCreatedDate(new Date());
        entity.setUserName(user.getUserName());
        entity.setFirstName(user.getFirstName());
        entity.setLastName(user.getLastName());
        entity.setPassword(user.getPassword());

        userDao.save(entity);
        return HttpStatus.OK;
    }
}
