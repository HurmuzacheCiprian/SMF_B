package com.smf.main.model;

import com.smf.main.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by cipriach on 22.12.2015.
 */
@Getter
@Setter
@ToString(of = {"expenseName", "expenseAmount", "category", "date"})
@NoArgsConstructor
public class ExpenseReport {
    private String expenseName;
    private Double expenseAmount;
    private Category category;
    private String date;

}
