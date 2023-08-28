package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int USER_MEAL_ID = START_SEQ + 3;
    public static final int MEAL_NOT_FOUND = 999999999;
    public static final Meal userMeal = new Meal(USER_MEAL_ID, LocalDateTime.of(2023, Month.JANUARY, 1, 6, 23, 0), "завтрак", 859);
    public static final Meal userMeal2 = new Meal(USER_MEAL_ID + 1, LocalDateTime.of(2023, Month.JANUARY, 1, 11, 23, 0), "обед", 458);
    public static final Meal userMeal3 = new Meal(USER_MEAL_ID + 2, LocalDateTime.of(2023, Month.JANUARY, 1, 20, 23, 0), "ужин", 745);
    public static final Meal userMeal4 = new Meal(USER_MEAL_ID + 3, LocalDateTime.of(2023, Month.JANUARY, 2, 8, 23, 0), "завтрак", 785);
    public static final Meal userMeal5 = new Meal(USER_MEAL_ID + 4, LocalDateTime.of(2023, Month.JANUARY, 2, 14, 23, 0), "обед", 565);
    public static final Meal userMeal6 = new Meal(USER_MEAL_ID + 5, LocalDateTime.of(2023, Month.JANUARY, 2, 21, 23, 0), "ужин", 985);
    public static final Meal userMeal7 = new Meal(USER_MEAL_ID + 6, LocalDateTime.of(2023, Month.JANUARY, 3, 8, 23, 0), "завтрак", 653);
    public static final Meal userMeal8 = new Meal(USER_MEAL_ID + 7, LocalDateTime.of(2023, Month.JANUARY, 3, 12, 23, 0), "обед", 845);
    public static final Meal userMeal9 = new Meal(USER_MEAL_ID + 8, LocalDateTime.of(2023, Month.JANUARY, 3, 23, 23, 0), "ужин", 456);
    public static final Meal createMeal = new Meal(USER_MEAL_ID + 18, LocalDateTime.of(2020, Month.JANUARY, 10, 11, 10, 10), "description", 999);
    public static final LocalDate START_DATE = LocalDate.of(2023, Month.JANUARY, 2);
    public static final LocalDate END_DATE = LocalDate.of(2023, Month.JANUARY, 3);
    public static final List<Meal> userMeals = Arrays.asList(
            new Meal(userMeal9),
            new Meal(userMeal8),
            new Meal(userMeal7),
            new Meal(userMeal6),
            new Meal(userMeal5),
            new Meal(userMeal4),
            new Meal(userMeal3),
            new Meal(userMeal2),
            new Meal(userMeal)
    );

    public static final List<Meal> userMealsBetweenInclusive = Arrays.asList(
            new Meal(userMeal9),
            new Meal(userMeal8),
            new Meal(userMeal7),
            new Meal(userMeal6),
            new Meal(userMeal5),
            new Meal(userMeal4)
    );

    public static Meal getNew() {
        return new Meal(LocalDateTime.of(2020, Month.JANUARY, 10, 11, 10, 10), "description", 999);
    }

    public static Meal getUpdated() {
        return new Meal(USER_MEAL_ID, LocalDateTime.of(1999, Month.JANUARY, 10, 10, 10, 10), "update", 1);
    }

    public static Meal getDuplicateDateTime() {
        return new Meal(userMeal.getDateTime(), "duplicateByDateTime", 1000);
    }
}
