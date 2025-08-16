package com.example.housewareecommerce.Utils;

import java.time.LocalDate;
import java.time.YearMonth;

public class DateUtils {
    public static LocalDate getFirstDayOfCurrentMonth() {
        return LocalDate.now().withDayOfMonth(1);
    }

    public static LocalDate getLastDayOfCurrentMonth() {
        YearMonth yearMonth = YearMonth.now();
        return yearMonth.atEndOfMonth();
    }
}
