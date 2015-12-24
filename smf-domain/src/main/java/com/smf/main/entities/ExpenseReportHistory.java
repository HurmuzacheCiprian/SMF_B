package com.smf.main.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Created by cipriach on 23.12.2015.
 */
@Entity
@Getter
@Setter
public class ExpenseReportHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "date", unique = true)
    private LocalDate localDate;

    @Column(unique = true)
    @Lob
    private String report;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id")
    private UserEntity userEntity;

}
