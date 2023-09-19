package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
@RequestMapping("/meals")
public class JspMealController {
    private static final Logger log = LoggerFactory.getLogger(JspMealController.class);

    @Autowired
    private MealService service;

    @RequestMapping(method = RequestMethod.GET)
    public String getAll(HttpServletRequest request) {
        log.info("getAll");
        int userId = SecurityUtil.authUserId();
        request.setAttribute("meals", MealsUtil.getTos(service.getAll(userId), SecurityUtil.authUserCaloriesPerDay()));
        return "meals";
    }


    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(HttpServletRequest request) {
        log.info("delete");
        service.delete(getId(request), SecurityUtil.authUserId());
        return ("redirect:http://localhost:8080/topjava/meals");
    }


    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(HttpServletRequest request) {
        final Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        request.setAttribute("meal", meal);
        return "mealForm";
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(HttpServletRequest request) {
        final Meal meal = service.get(getId(request), SecurityUtil.authUserId());
        request.setAttribute("meal", meal);
        return "mealForm";
    }

    @RequestMapping(value = "/filter", method = RequestMethod.GET)
    public String filter(HttpServletRequest request) {
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
        List<MealTo> mealsTo = MealsUtil.getFilteredTos(service.getBetweenInclusive(startDate, endDate, SecurityUtil.authUserId()),
                SecurityUtil.authUserCaloriesPerDay(), startTime, endTime);
        request.setAttribute("meals", mealsTo);
        return "meals";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String save(HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        if (StringUtils.hasLength(request.getParameter("id"))) {
            meal.setId(Integer.valueOf(request.getParameter("id")));
            service.update(meal, SecurityUtil.authUserId());
        } else {
            service.create(meal, SecurityUtil.authUserId());
        }
        return "redirect:http://localhost:8080/topjava/meals";
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
