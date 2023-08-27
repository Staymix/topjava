package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int MEAL_ID = 1;
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;
    public static final int MEAL_NOT_FOUND = 999999999;
    public static final Meal MEAL = new Meal(MEAL_ID, LocalDateTime.of(2023, Month.JANUARY, 1, 10, 23, 0), "завтрак", 500);
    public static final Meal NEW_MEAL = new Meal(7, LocalDateTime.of(2023, Month.JANUARY, 1, 10, 23, 0), "завтрак", 500);
    public static final LocalDate START_END_DATE = LocalDate.of(2023, Month.JANUARY, 1);
    public static final List<Meal> MEALS_USER = Arrays.asList(
            new Meal(3, LocalDateTime.of(2023, Month.JANUARY, 1, 17, 23, 0), "ужин", 1000),
            new Meal(2, LocalDateTime.of(2023, Month.JANUARY, 1, 13, 23, 0), "обед", 800),
            new Meal(1, LocalDateTime.of(2023, Month.JANUARY, 1, 10, 23, 0), "завтрак", 500)
    );

    public static Meal getNew() {
        return new Meal(LocalDateTime.of(2020, Month.JANUARY, 10, 11, 10, 10), "description", 999);
    }

    public static Meal getUpdated() {
        return new Meal(1, LocalDateTime.of(1999, Month.JANUARY, 10, 10, 10, 10, 10), "update", 1);
    }

    public static Meal getDuplicateDateTime() {
        return new Meal(LocalDateTime.of(2023, Month.JANUARY, 1, 17, 23, 0), "duplicate", 1000);
    }
}
