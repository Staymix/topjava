package ru.javawebinar.topjava.service.jdbc;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;

import javax.validation.ConstraintViolationException;
import java.util.Set;

import static ru.javawebinar.topjava.Profiles.JDBC;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;

@ActiveProfiles(JDBC)
public class JdbcUserServiceTest extends AbstractUserServiceTest {

    @Test
    public void updateWithoutRoles() {
        User admin = service.get(ADMIN_ID);
        admin.setRoles(Set.of());
        Assert.assertThrows(ConstraintViolationException.class, () -> service.update(admin));
    }
}