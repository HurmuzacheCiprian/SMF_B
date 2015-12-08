package com.smf.main.resources;

import com.smf.main.CredentialsService;
import com.smf.main.domain.UserDao;
import com.smf.main.model.User;
import com.smf.main.model.UserRegistration;
import com.smf.main.model.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by cipriach on 07.12.2015.
 */
@RestController
public class CredentialsResource {

    private UserDao userDao;

    private CredentialsService credentialsService;

    @Autowired
    public CredentialsResource(UserDao userDao, CredentialsService credentialsService) {
        this.userDao = userDao;
        this.credentialsService = credentialsService;
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST, produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<UserResponse> checkLoginCredentials(@RequestBody User user) {

        boolean isUserExisting = credentialsService.checkUser(user);

        return isUserExisting == false ?
                new ResponseEntity(UserResponse.builder().isOk(false).build(), HttpStatus.NOT_FOUND) :
                new ResponseEntity(UserResponse.builder().isOk(true).build(), HttpStatus.OK);

    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public HttpStatus registerUser(@RequestBody UserRegistration user) {
        return credentialsService.registerUser(user) == true ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
    }
}
