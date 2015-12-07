package com.smf.main.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by cipriach on 07.12.2015.
 */
@Entity
@Getter
@Setter
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String userName;

    private String password;

    private Date createdDate;

    private String firstName;

    private String lastName;

    private Date lastLoggedIn;

    private Integer logginTries;
}
