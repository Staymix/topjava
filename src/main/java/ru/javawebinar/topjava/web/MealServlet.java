package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

//        private MealRepository repository;

    private MealRestController mealRestController;
    private ConfigurableApplicationContext appCtx;

    @Override
    public void init() {
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        mealRestController = appCtx.getBean(MealRestController.class);
//        repository = new InMemoryMealRepository();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        Meal meal;
        boolean isEmptyId = id.isEmpty();
        meal = new Meal((isEmptyId ? null : Integer.valueOf(id)), LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"), Integer.parseInt(request.getParameter("calories")),
                SecurityUtil.authUserId());
        log.info(meal.isNew() ? "Create {}" : "Update {}", meal);
        if (isEmptyId) {
            mealRestController.save(meal);
        } else {
            mealRestController.update(meal);
        }
        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                log.info("Delete id={}", id);
                mealRestController.delete(id);
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000,
                                SecurityUtil.authUserId()) : mealRestController.get(getId(request));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "filter":
                log.info("Filter");
                String startDate = request.getParameter("startDate");
                request.setAttribute("startDate", startDate);
                String endDate = request.getParameter("endDate");
                request.setAttribute("endDate", endDate);
                String startTime = request.getParameter("startTime");
                request.setAttribute("startTime", startTime);
                String endTime = request.getParameter("endTime");
                request.setAttribute("endTime", endTime);
                request.setAttribute("meals", MealsUtil.getFilteredMealTo(mealRestController.getAll(),
                        (startDate.isEmpty() ? LocalDate.MIN : LocalDate.parse(startDate, DateTimeFormatter.ISO_LOCAL_DATE)),
                        (endDate.isEmpty() ? LocalDate.MAX : LocalDate.parse(endDate, DateTimeFormatter.ISO_LOCAL_DATE)),
                        (startTime.isEmpty() ? LocalTime.MIN : LocalTime.parse(startTime, DateTimeFormatter.ISO_LOCAL_TIME)),
                        (endTime.isEmpty() ? LocalTime.MAX : LocalTime.parse(endTime, DateTimeFormatter.ISO_LOCAL_TIME))));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
            case "all":
            default:
                log.info("GetAll");
                request.setAttribute("meals", mealRestController.getAll());
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }

    @Override
    public void destroy() {
        appCtx.close();
    }
}
