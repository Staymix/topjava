package ru.javawebinar.topjava.service.datajpa;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaUserServiceTest extends AbstractUserServiceTest {

    @Test
    public void withMeals() {
        User userWithMeals = service.getWithMeals(USER_ID);
        USER_MATCHER.assertMatch(userWithMeals, user);
    }

    @Test
    public void getWithMealsNotFound() {
        Assert.assertThrows(NotFoundException.class, () -> service.getWithMeals(NOT_FOUND));
    }
}
