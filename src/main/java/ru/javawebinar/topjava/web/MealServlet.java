package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.InMemoryMealRepository;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {

    private static final Logger log = getLogger(MealServlet.class);

    private MealRepository repository;

    private static final int LIMIT_CALORIES = 2000;

    @Override
    public void init() throws ServletException {
        repository = new InMemoryMealRepository();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = "unknown";
        }
        Meal meal;
        int id;
        switch (action) {
            case "delete":
                id = Integer.parseInt(req.getParameter("id"));
                log.info("Deleting meal with id: " + id);
                repository.delete(id);
                resp.sendRedirect("meals");
                return;
            case "update":
                id = Integer.parseInt(req.getParameter("id"));
                log.info("Updating meal with id: " + id);
                meal = repository.getAll(id);
                break;
            case "create":
                log.info("Creating new Meal");
                meal = new Meal(LocalDateTime.now().withSecond(0).withNano(0), "", 1);
                break;
            default:
                log.info("Default, Unknown action");
                req.setAttribute("meals", MealsUtil.getWithExceeded(new ArrayList<>(repository.getMeals()), LIMIT_CALORIES));
                req.getRequestDispatcher("/meals.jsp").forward(req, resp);
                return;
        }
        req.setAttribute("meals", meal);
        log.info("Forwarding to editMeal.jsp");
        req.getRequestDispatcher("/editMeal.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Processing a meal addition request...");
        req.setCharacterEncoding("UTF-8");
        Integer id = req.getParameter("id").isEmpty() ? null : Integer.parseInt(req.getParameter("id"));
        repository.save(new Meal(id, LocalDateTime.parse(req.getParameter("date"), DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                req.getParameter("description"), Integer.parseInt(req.getParameter("calories"))));
        resp.sendRedirect("meals");
        log.info("Meal added successfully.");
    }
}