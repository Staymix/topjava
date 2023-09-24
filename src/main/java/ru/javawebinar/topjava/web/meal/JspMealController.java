package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
@RequestMapping("/meals")
public class JspMealController extends AbstractMealController {

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("meals", super.getAll());
        return "meals";
    }

    @GetMapping("/delete")
    public String remove(@RequestParam int id) {
        super.delete(id);
        return ("redirect:/meals");
    }

    @GetMapping("/create")
    public String create(Model model) {
        final Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @GetMapping("/update")
    public String update(@RequestParam int id, Model model) {
        final Meal meal = service.get(id, SecurityUtil.authUserId());
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @GetMapping("/filter")
    public String filter(@RequestParam String startDate,
                         @RequestParam String endDate,
                         @RequestParam String startTime,
                         @RequestParam String endTime,
                         Model model) {
        model.addAttribute("meals", super.getBetween(parseLocalDate(startDate), parseLocalTime(startTime),
                parseLocalDate(endDate), parseLocalTime(endTime)));
        return "meals";
    }

    @PostMapping
    public String save(@RequestParam String dateTime,
                       @RequestParam String description,
                       @RequestParam int calories,
                       @RequestParam String id) {
        Meal meal = new Meal(LocalDateTime.parse(dateTime), description, calories);
        if (StringUtils.hasLength(id)) {
            meal.setId(Integer.valueOf(id));
            super.update(meal, Integer.parseInt(id));
        } else {
            super.create(meal);
        }
        return "redirect:/meals";
    }
}
