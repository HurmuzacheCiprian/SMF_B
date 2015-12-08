package com.smf.main.model;

import com.smf.main.Category;
import lombok.*;

/**
 * Created by cipriach on 08.12.2015.
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExpensesResponse {

    private Category category;
    private Long amount;
}
