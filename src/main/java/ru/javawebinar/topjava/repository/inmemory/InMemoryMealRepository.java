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
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
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
            repository.put(meal.getId(), meal);
            return meal;
        }
        Meal existingMeal = repository.get(meal.getId());
        if (existingMeal != null && existingMeal.getUserId().equals(userId)) {
            meal.setUserId(userId);
            return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        }
        return null;
    }

    @Override
    public boolean delete(int id, int userId) {
        Meal meal = repository.get(id);
        if (meal != null) {
            return MealsUtil.belongsToUser(meal, userId) && repository.remove(id) != null;
        }
        return false;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = repository.get(id);
        return MealsUtil.belongsToUser(meal, userId) ? meal : null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return getMealByPredicate(meal -> meal.getUserId() == userId, Comparator.comparing(Meal::getDateTime).reversed());
    }

    @Override
    public List<Meal> filterByDate(LocalDate startDate, LocalDate endDate, int userId) {
        return getMealByPredicate(meal -> !meal.getDate().isBefore(startDate)
                && !meal.getDate().isAfter(endDate), Comparator.comparing(Meal::getDateTime).reversed());
    }

    private List<Meal> getMealByPredicate(Predicate<Meal> filter, Comparator<Meal> sorted) {
        return repository.values().stream()
                .filter(filter)
                .sorted(sorted)
                .collect(Collectors.toList());
    }
}