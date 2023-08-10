package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.ValidationUtil;

import java.util.List;

import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Service
public class MealService {

    private final MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal save(Meal meal) {
        return repository.save(authUserId(), meal);
    }

    public Meal update(Meal meal) {
        return ValidationUtil.checkNotFound(repository.save(authUserId(), meal), String.valueOf(authUserId()));
    }

    public void delete(int id) {
        ValidationUtil.checkNotFoundWithId(repository.delete(authUserId(), id), id);
    }

    public Meal get(int id) {
        return ValidationUtil.checkNotFound(repository.get(authUserId(), id), String.valueOf(id));
    }

    public List<Meal> getAll() {
        return (List<Meal>) repository.getAll(authUserId());
    }
}