package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

@Service
public class MealService {

    private final MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal create(Meal meal, int userId) {
        return repository.save(meal, userId);
    }

    public Meal update(Meal meal, int userId) {
        Meal updateMeal = repository.save(meal, userId);
        if (updateMeal == null) {
            throw new NotFoundException("Meal id=" + meal.getId() + " does not belong to the user id=" + userId);
        }
        return updateMeal;
    }

    public void delete(int id, int userId) {
        MealsUtil.belongsToUser(get(id, userId), userId);
        ValidationUtil.checkNotFoundWithId(repository.delete(id, userId), id);
    }

    public Meal get(int id, int userId) {
        Meal meal = repository.get(id, userId);
        if (!MealsUtil.belongsToUser(meal, userId)) {
            throw new NotFoundException("Meal with id=" + id + " is not present for user with id=" + userId);
        }
        return meal;
    }

    public List<Meal> getAll(int userId) {
        return repository.getAll(userId);
    }

    public List<Meal> filterByDate(LocalDate startDate, LocalDate endDate, int userId) {
        return repository.filterByDate(startDate, endDate, userId);
    }
}