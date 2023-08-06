package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.util.Collection;
import java.util.List;

public interface MealRepository {

    void deleteMeal(Integer id);

    void addMeal(Meal meal);

    Meal getMeal(Integer id);

    Collection<Meal> getMeals();
}
