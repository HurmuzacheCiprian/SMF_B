package com.smf.main.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by cipriach on 07.12.2015.
 */
@NoArgsConstructor
@Getter
@Setter
public class UserRegistration extends User {
    private String firstName;
    private String lastName;
    private String password;
}
