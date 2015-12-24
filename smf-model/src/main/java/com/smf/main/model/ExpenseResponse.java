package com.smf.main.model;

import com.smf.main.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by cipriach on 08.12.2015.
 */
@Builder
@Getter
@Setter
public class ExpenseResponse {
    //private List<ExpensesResponse> expense;
    private Long id;
    private String expenseName;
    private String createdDate;
    private Category category;
    private Long amount;
}
