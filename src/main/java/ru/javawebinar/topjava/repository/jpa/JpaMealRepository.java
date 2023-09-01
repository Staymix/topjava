package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaMealRepository implements MealRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            User ref = em.getReference(User.class, userId);
            meal.setUser(ref);
            em.persist(meal);
        } else {
            Meal existingMeal = em.find(Meal.class, meal.getId());
            if (existingMeal == null || existingMeal.getUser().id() != userId) {
                return null;
            }
            existingMeal.setDateTime(meal.getDateTime());
            existingMeal.setDescription(meal.getDescription());
            existingMeal.setCalories(meal.getCalories());
        }
        return meal;
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        return em.createQuery("DELETE Meal m WHERE m.id=:id AND user.id=:userId")
                .setParameter("id", id)
                .setParameter("userId", userId)
                .executeUpdate() != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        TypedQuery<Meal> query = em.createQuery("SELECT m FROM Meal m WHERE m.id=:id AND m.user.id=:userId", Meal.class)
                .setParameter("id", id)
                .setParameter("userId", userId);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Meal> getAll(int userId) {
        TypedQuery<Meal> query = em.createQuery("SELECT m FROM Meal m WHERE m.user.id=:userId ORDER BY dateTime DESC", Meal.class)
                .setParameter("userId", userId);
        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        TypedQuery<Meal> query = em.createQuery("SELECT m FROM Meal m WHERE user.id=:userId " +
                        "AND m.dateTime >=:startDateTime AND m.dateTime <:endDateTime ORDER BY dateTime DESC", Meal.class)
                .setParameter("userId", userId)
                .setParameter("startDateTime", startDateTime)
                .setParameter("endDateTime", endDateTime);
        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
    }
}