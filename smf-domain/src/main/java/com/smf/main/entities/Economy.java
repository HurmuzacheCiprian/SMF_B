package com.smf.main.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by cipriach on 08.12.2015.
 */
@Entity
@Getter
@Setter
public class Economy {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long amount;
    private Date createdDate;
    private String info;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private UserEntity userEntity;

}
