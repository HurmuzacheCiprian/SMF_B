package com.smf.main.model;

import com.smf.main.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by cipriach on 22.12.2015.
 */
@Builder
@Getter
@Setter
@ToString(of = {"expenseName", "expenseAmount", "category", "date"})
public class ExpenseReport {
    private String expenseName;
    private Long expenseAmount;
    private Category category;
    private String date;

}
