package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(1, meal));
    }

    @Override
    public Meal save(int userId, Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            if (meal.getUserId() == null) meal.setUserId(userId);
            repository.put(meal.getId(), meal);
            return meal;
        }
        getAll(userId).stream()
                .filter(meal1 -> meal.getId().equals(meal1.getId()))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(
                        "Meal id: " + meal.getId() + " does not belong to the user id: " + userId));
        meal.setUserId(userId);
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int userId, int id) {
        ValidationUtil.checkNotFoundWithId(repository.get(id), id);
        if (MealsUtil.belongsToUser(repository.get(id), userId)) return repository.remove(id) != null;
        return false;
    }

    @Override
    public Meal get(int userId, int id) {
        ValidationUtil.checkNotFoundWithId(repository.get(id), id);
        if (MealsUtil.belongsToUser(repository.get(id), userId)) return repository.get(id);
        return null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return repository.values().stream()
                .filter(meal -> meal.getUserId() == userId)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<Meal> filterByDate(LocalDate startDate, LocalDate endDate, int userId) {
        return repository.values().stream()
                .filter(meal -> meal.getUserId() == userId)
                .filter(meal -> !meal.getDate().isBefore(startDate) && !meal.getDate().isAfter(endDate))
                .collect(Collectors.toList());
    }
}
