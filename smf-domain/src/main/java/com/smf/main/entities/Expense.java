package com.smf.main.entities;

import com.smf.main.Category;
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
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long amount;
    private String expenseName;
    @Enumerated(EnumType.STRING)
    private Category category;
    private Date date;
    private boolean isComputed = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id")
    private UserEntity userEntity;
}
