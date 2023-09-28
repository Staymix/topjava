package ru.javawebinar.topjava.web.meal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.ActiveDbProfileResolver;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.UserTestData.user;

@Transactional
@ActiveProfiles(resolver = ActiveDbProfileResolver.class, profiles = Profiles.REPOSITORY_IMPLEMENTATION)
public class MealRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = MealRestController.REST_URL + '/';

    @Autowired
    private MealService service;


    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + MEAL1_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MEAL_MATCHER.contentJson(meal1));
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + MEAL1_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        Assertions.assertThrows(NotFoundException.class, () -> service.get(MEAL1_ID, USER_ID));
    }

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MEAL_TO_MATCHER.contentJson(MealsUtil.getTos(meals, user.getCaloriesPerDay())));
    }

    @Test
    void createWithLocation() throws Exception {
        Meal newMeal = MealTestData.getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newMeal)))
                .andExpect(status().isCreated());
        Meal created = MEAL_MATCHER.readFromJson(action);
        newMeal.setId(NEW_MEAL_ID);
        MEAL_MATCHER.assertMatch(created, newMeal);
        MEAL_MATCHER.assertMatch(service.get(NEW_MEAL_ID, USER_ID), newMeal);
    }

    @Test
    void update() throws Exception {
        Meal updateMeal = MealTestData.getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL + MEAL1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updateMeal)))
                .andExpect(status().isNoContent());
        MEAL_MATCHER.assertMatch(service.get(MEAL1_ID, USER_ID), MealTestData.getUpdated());
    }

    @Test
    void getBetween() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "filter")
                .param("startDate", "2020-01-30").param("startTime", "11:00:00")
                .param("endDate", "2020-01-30").param("endTime", "23:59:59"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MEAL_TO_MATCHER.contentJson(MealsUtil.getTos(List.of(meal3, meal2), user.getCaloriesPerDay())));
    }

    @Test
    void filterWithoutDateTime() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "filter?startDate=&startTime=&endDate=&endTime="))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MEAL_TO_MATCHER.contentJson(MealsUtil.getTos(meals, user.getCaloriesPerDay())));
    }
}
