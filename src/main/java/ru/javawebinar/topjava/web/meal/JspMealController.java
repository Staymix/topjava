package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
@RequestMapping("/meals")
public class JspMealController extends AbstractMealController {

    @GetMapping
    public String getAll(Model model) {
        log.info("getAll");
        model.addAttribute("meals", MealsUtil.getTos(service.getAll(SecurityUtil.authUserId()),
                SecurityUtil.authUserCaloriesPerDay()));
        return "meals";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam(value = "id") int id) {
        log.info("delete");
        service.delete(id, SecurityUtil.authUserId());
        return ("redirect:/meals");
    }

    @GetMapping("/create")
    public String create(Model model) {
        final Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @GetMapping("/update")
    public String update(@RequestParam(value = "id") int id, Model model) {
        final Meal meal = service.get(id, SecurityUtil.authUserId());
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @GetMapping("/filter")
    public String filter(@RequestParam(value = "startDate") String startDate,
                         @RequestParam(value = "endDate") String endDate,
                         @RequestParam(value = "startTime") String startTime,
                         @RequestParam(value = "endTime") String endTime,
                         Model model) {
        List<MealTo> mealsTo = MealsUtil.getFilteredTos(service.getBetweenInclusive(
                        parseLocalDate(startDate), parseLocalDate(endDate), SecurityUtil.authUserId()),
                SecurityUtil.authUserCaloriesPerDay(), parseLocalTime(startTime), parseLocalTime(endTime));
        model.addAttribute("meals", mealsTo);
        return "meals";
    }

    @PostMapping
    public String save(@RequestParam(value = "dateTime") String dateTime,
                       @RequestParam(value = "description") String description,
                       @RequestParam(value = "calories") int calories,
                       @RequestParam(value = "id") String id) {
        Meal meal = new Meal(LocalDateTime.parse(dateTime), description, calories);
        if (StringUtils.hasLength(id)) {
            meal.setId(Integer.valueOf(id));
            service.update(meal, SecurityUtil.authUserId());
        } else {
            service.create(meal, SecurityUtil.authUserId());
        }
        return "redirect:/meals";
    }
}
