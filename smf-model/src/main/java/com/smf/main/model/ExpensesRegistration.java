package com.smf.main.model;

import com.smf.main.Category;
import lombok.*;

/**
 * Created by cipriach on 08.12.2015.
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExpensesRegistration {

    private Long amount;
    private Category category;
    private String info;
}