package ru.javawebinar.topjava.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateFormatter {
    private DateFormatter(){}

    public static String formatLocalDateTime(LocalDateTime ldt, String pattern) {
        return ldt.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static LocalDateTime parse(String date) {
        String[] dates = date.split("[-T:]");
        return LocalDateTime.of(Integer.parseInt(dates[0]), Integer.parseInt(dates[1]), Integer.parseInt(dates[2]),
                Integer.parseInt(dates[3]), Integer.parseInt(dates[4]));
    }
}
