package com.smf.main.model;

import com.smf.main.Category;
import lombok.*;

import java.util.List;

/**
 * Created by cipriach on 08.12.2015.
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExpensesResponse {

    private List<ExpenseResponse> expenses;
    private int totalElements;
}
