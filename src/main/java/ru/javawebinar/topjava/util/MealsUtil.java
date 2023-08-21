package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MealsUtil {
    public static final int DEFAULT_CALORIES_PER_DAY = 2000;

    public static final List<Meal> meals = Arrays.asList(
            new Meal(LocalDateTime.of(2023, Month.JANUARY, 30, 10, 0), "Завтрак", 500, 1),
            new Meal(LocalDateTime.of(2023, Month.JANUARY, 30, 13, 0), "Обед", 1000, 1),
            new Meal(LocalDateTime.of(2023, Month.JANUARY, 30, 20, 0), "Ужин", 500, 1),
            new Meal(LocalDateTime.of(2023, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100, 1),
            new Meal(LocalDateTime.of(2023, Month.JANUARY, 31, 10, 0), "Завтрак", 1000, 1),
            new Meal(LocalDateTime.of(2023, Month.JANUARY, 31, 13, 0), "Обед", 500, 1),
            new Meal(LocalDateTime.of(2023, Month.JANUARY, 31, 20, 0), "Ужин", 410, 1)
    );

    public static final List<Meal> meals2 = Arrays.asList(
            new Meal(LocalDateTime.of(2023, Month.JULY, 10, 10, 0), "Кофе", 100, 2),
            new Meal(LocalDateTime.of(2023, Month.JULY, 10, 13, 0), "Перекус", 500, 2),
            new Meal(LocalDateTime.of(2023, Month.JULY, 10, 16, 0), "Полдник", 500, 2),
            new Meal(LocalDateTime.of(2023, Month.JULY, 1, 0, 0), "Ужин", 1000, 2),
            new Meal(LocalDateTime.of(2023, Month.JULY, 1, 10, 0), "Чай", 10, 2),
            new Meal(LocalDateTime.of(2023, Month.JULY, 1, 13, 0), "Обед", 700, 2),
            new Meal(LocalDateTime.of(2023, Month.JULY, 1, 20, 0), "Фастфуд", 800, 2)
    );

    public static List<MealTo> getTos(Collection<Meal> meals, int caloriesPerDay) {
        return filterByPredicate(meals, caloriesPerDay, meal -> true);
    }

    public static List<MealTo> getFilteredByTime(List<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        return getTos(meals, caloriesPerDay).stream()
                .filter(meal -> DateTimeUtil.isBetweenHalfOpen(meal.getTime(), startTime, endTime))
                .collect(Collectors.toList());
    }

    public static boolean belongsToUser(Meal meal, int userId) {
        return meal.getUserId() == userId;
    }

    private static List<MealTo> filterByPredicate(Collection<Meal> meals, int caloriesPerDay, Predicate<Meal> filter) {
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
//                      Collectors.toMap(Meal::getDate, Meal::getCalories, Integer::sum)
                );

        return meals.stream()
                .filter(filter)
                .map(meal -> createTo(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    private static MealTo createTo(Meal meal, boolean excess) {
        return new MealTo(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }
}
