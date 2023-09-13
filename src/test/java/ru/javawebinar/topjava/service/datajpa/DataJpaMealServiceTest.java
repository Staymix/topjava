package ru.javawebinar.topjava.service.datajpa;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.AbstractMealServiceTest;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.UserTestData.USER_MATCHER;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaMealServiceTest extends AbstractMealServiceTest {

    @Test
    public void withUser() {
        Meal mealWithUser = service.getWithUser(MEAL1_ID, USER_ID);
        MEAL_MATCHER.assertMatch(mealWithUser, meal1);
        User user = mealWithUser.getUser();
        USER_MATCHER.assertMatch(user, UserTestData.user);
    }

    @Test
    public void getWithUserNotFound() {
        Assert.assertThrows(NotFoundException.class, () -> service.getWithUser(NOT_FOUND, USER_ID));
    }
}
