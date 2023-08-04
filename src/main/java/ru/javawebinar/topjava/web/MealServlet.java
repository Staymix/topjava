package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.DateFormatter;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@WebServlet("/meals")
public class MealServlet extends HttpServlet {

    private static final Logger log = getLogger(MealServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("redirect to users");
        String action = req.getParameter("action");
        if (action == null) {
            List<MealTo> list = MealsUtil.getWithExceeded(MealsUtil.meals, 2000);
            req.setAttribute("meals", Collections.synchronizedList(list));
            req.getRequestDispatcher("/meals.jsp").forward(req, resp);
            return;
        }
        Meal meal;
        switch (action) {
            case "delete":
                MealsUtil.meals.remove(Integer.parseInt(req.getParameter("index")));
                resp.sendRedirect("meals");
                return;
            case "update":
                meal = MealsUtil.meals.get(Integer.parseInt(req.getParameter("index")));
                break;
            case "create":
                meal = Meal.EMPTY;
                break;
            default:
                throw new IllegalArgumentException("Action : " + action + " is illegal");
        }
        req.setAttribute("meals", meal);
        req.getRequestDispatcher("/edit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        int id = Integer.parseInt(req.getParameter("id"));
        if (id > 0) {
            MealsUtil.meals.remove(--id);
        }
        MealsUtil.meals.add(new Meal(DateFormatter.parse(req.getParameter("date")),
                req.getParameter("description"), Integer.parseInt(req.getParameter("calories"))));
        resp.sendRedirect("meals");
    }
}