package com.smf.main.model;

import com.smf.main.Category;
import lombok.*;

/**
 * Created by cipriach on 22.12.2015.
 */
@Getter
@Setter
@ToString(of = {"expenseName", "expenseAmount", "category", "date"})
@NoArgsConstructor
public class ExpenseReport {
    private String expenseName;
    private Long expenseAmount;
    private Category category;
    private String date;

}
