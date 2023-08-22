package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(meal, meal.getUserId()));
        MealsUtil.meals2.forEach(meal -> save(meal, meal.getUserId()));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
            Map<Integer, Meal> meals = repository.computeIfAbsent(userId, userMeals -> new ConcurrentHashMap<>());
            meals.put(meal.getId(), meal);
            return meal;
        }
        meal.setUserId(userId);
        return repository.get(userId).computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        if (isExist(userId)) {
            Map<Integer, Meal> userMeals = repository.get(userId);
            return userMeals != null && userMeals.remove(id) != null;
        }
        return false;
    }

    @Override
    public Meal get(int id, int userId) {
        if (isExist(userId)) {
            Map<Integer, Meal> userMeals = repository.get(userId);
            return userMeals == null ? null : userMeals.get(id);
        }
        return null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return filteredByPredicate(meal -> true, userId);
    }

    @Override
    public List<Meal> filterByDate(LocalDate startDate, LocalDate endDate, int userId) {
        return filteredByPredicate(meal -> !meal.getDate().isBefore(startDate)
                && !meal.getDate().isAfter(endDate), userId);
    }

    private List<Meal> filteredByPredicate(Predicate<Meal> filter, int userId) {
        if (isExist(userId)) {
            Map<Integer, Meal> userMeals = repository.get(userId);
            return userMeals == null ? null : userMeals.values().stream()
                    .filter(filter)
                    .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                    .collect(Collectors.toList());
        }
        return null;
    }

    private boolean isExist(int userId) {
        return repository.containsKey(userId);
    }
}