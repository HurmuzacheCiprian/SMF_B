package com.smf.main.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by cipriach on 30.12.2015.
 */
@Builder
@Getter
@Setter
public class PeriodicCategoryReport {

    private List<String> months;
    private List<Double> expensesAmount;
    private List<Double> remainingFunds;

}
