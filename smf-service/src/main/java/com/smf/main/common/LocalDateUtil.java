package com.smf.main.common;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cipriach on 30.12.2015.
 */
public final class LocalDateUtil {

    private LocalDateUtil() {
        throw new UnsupportedOperationException("This should not be called");
    }

    /*
        If now is DECEMBER and the period is 4 it will display
            AUGUST
            SEPTEMBER
            OCTOBER
            NOVEMBER
     */
    public static List<String> getLastPeriodicMonths(LocalDate now, int period) {
        List<String> months = new ArrayList<>(period);
        LocalDate first = now.minusMonths(period);

        while (first.getMonthValue() != now.getMonthValue()) {
            months.add(first.getMonth().name());
            first = first.plusMonths(1L);
        }
        return months;
    }

    public static List<Integer> getLastPeriodicMonthValues(LocalDate now, int period) {
        List<Integer> months = new ArrayList<>(period);
        LocalDate first = now.minusMonths(period);

        while (first.getMonthValue() != now.getMonthValue()) {
            months.add(first.getMonthValue());
            first = first.plusMonths(1L);
        }
        return months;
    }
}
