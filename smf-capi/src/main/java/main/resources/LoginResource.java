package main.resources;

import main.model.User;
import main.model.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by cipriach on 07.12.2015.
 */
@RestController
public class LoginResource {

    @RequestMapping(path = "/login", method = RequestMethod.POST, produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<UserResponse> checkLoginCredentials(@RequestBody User user) {
        if ("gigi".equals(user.getUserName()) && "gigi".equals(user.getPassword())) {
            return new ResponseEntity(UserResponse.builder().isOk(true).build(), HttpStatus.OK);
        }

        return new ResponseEntity(UserResponse.builder().isOk(false).build(), HttpStatus.NOT_FOUND);

    }
}
