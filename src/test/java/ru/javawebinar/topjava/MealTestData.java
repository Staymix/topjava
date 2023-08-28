package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int MEAL_ID = 100003;
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;
    public static final int MEAL_NOT_FOUND = 999999999;
    public static final Meal MEAL = new Meal(MEAL_ID, LocalDateTime.of(2023, Month.JANUARY, 1, 6, 23, 0), "завтрак", 859);
    public static final Meal NEW_MEAL = new Meal(100021, LocalDateTime.of(2023, Month.JANUARY, 1, 10, 23, 0), "завтрак", 500);
    public static final LocalDate START_DATE = LocalDate.of(2023, Month.JANUARY, 2);
    public static final LocalDate END_DATE = LocalDate.of(2023, Month.JANUARY, 3);
    public static final List<Meal> MEALS_USER = Arrays.asList(
            new Meal(100011, LocalDateTime.of(2023, Month.JANUARY, 3, 23, 23, 0), "ужин", 456),
            new Meal(100010, LocalDateTime.of(2023, Month.JANUARY, 3, 12, 23, 0), "обед", 845),
            new Meal(100009, LocalDateTime.of(2023, Month.JANUARY, 3, 8, 23, 0), "завтрак", 653),
            new Meal(100008, LocalDateTime.of(2023, Month.JANUARY, 2, 21, 23, 0), "ужин", 985),
            new Meal(100007, LocalDateTime.of(2023, Month.JANUARY, 2, 14, 23, 0), "обед", 565),
            new Meal(100006, LocalDateTime.of(2023, Month.JANUARY, 2, 8, 23, 0), "завтрак", 785),
            new Meal(100005, LocalDateTime.of(2023, Month.JANUARY, 1, 20, 23, 0), "ужин", 745),
            new Meal(100004, LocalDateTime.of(2023, Month.JANUARY, 1, 11, 23, 0), "обед", 458),
            new Meal(100003, LocalDateTime.of(2023, Month.JANUARY, 1, 6, 23, 0), "завтрак", 859)
    );

    public static final List<Meal> MEALS_USER_BETWEEN_INCLUSIVE = Arrays.asList(
            new Meal(100011, LocalDateTime.of(2023, Month.JANUARY, 3, 23, 23, 0), "ужин", 456),
            new Meal(100010, LocalDateTime.of(2023, Month.JANUARY, 3, 12, 23, 0), "обед", 845),
            new Meal(100009, LocalDateTime.of(2023, Month.JANUARY, 3, 8, 23, 0), "завтрак", 653),
            new Meal(100008, LocalDateTime.of(2023, Month.JANUARY, 2, 21, 23, 0), "ужин", 985),
            new Meal(100007, LocalDateTime.of(2023, Month.JANUARY, 2, 14, 23, 0), "обед", 565),
            new Meal(100006, LocalDateTime.of(2023, Month.JANUARY, 2, 8, 23, 0), "завтрак", 785)
    );

    public static Meal getNew() {
        return new Meal(LocalDateTime.of(2020, Month.JANUARY, 10, 11, 10, 10), "description", 999);
    }

    public static Meal getUpdated() {
        return new Meal(MEAL_ID, LocalDateTime.of(1999, Month.JANUARY, 10, 10, 10, 10, 10), "update", 1);
    }

    public static Meal getDuplicateDateTime() {
        return new Meal(LocalDateTime.of(2023, Month.JANUARY, 1, 20, 23, 0), "duplicate", 1000);
    }
}
