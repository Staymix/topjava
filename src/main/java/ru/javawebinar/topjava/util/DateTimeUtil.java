package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static boolean isBetweenHalfOpen(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) < 0;
    }

    public static boolean isBetweenClosed(LocalDateTime ldt, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        LocalDate ld = ldt.toLocalDate();
        if (ld.compareTo(startDate) >= 0 && ld.compareTo(endDate) <= 0) {
            LocalTime lt = ldt.toLocalTime();
            return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) < 0;
        }
        return false;
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}

