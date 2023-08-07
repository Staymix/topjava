package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;

public interface MealRepository {

    void delete(int id);

    Meal save(Meal meal);

    Meal getAll(int id);

    Collection<Meal> getMeals();
}
