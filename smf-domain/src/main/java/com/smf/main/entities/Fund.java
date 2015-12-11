package com.smf.main.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by cipriach on 08.12.2015.
 */
@Entity
@Table(
        uniqueConstraints =
        @UniqueConstraint(columnNames = {"fk_user", "name"})
)
@Getter
@Setter
public class Fund {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private Long amount;
    private Date createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id",name = "fk_user")
    private UserEntity userEntity;
}
