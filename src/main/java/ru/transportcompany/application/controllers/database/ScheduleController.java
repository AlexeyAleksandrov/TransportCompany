package ru.transportcompany.application.controllers.database;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.transportcompany.application.models.database.Route;
import ru.transportcompany.application.repositories.RouteRepository;
import ru.transportcompany.application.repositories.ScheduleRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.SimpleFormatter;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/schedule")
@AllArgsConstructor
public class ScheduleController
{
    private ScheduleRepository scheduleRepository;
    private RouteRepository routeRepository;

    @GetMapping(value = "/show")
    public String show()
    {
        return "schedule/show";
    }

    @PostMapping(value = "/show")
    public String showDate(@RequestParam String date, Model model)
    {
        List<Route> routes = new ArrayList<>();
        try
        {
            System.out.println("Дата: " + date);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date searchDate = dateFormat.parse(date);

            System.out.println("Дата: " + searchDate.toString() + " День: " + searchDate.getDate() + " День недели: " + searchDate.getDay());

            // ищем подходящие маршруты
            routes = routeRepository.findAll().stream()
                    .filter(route -> {
                        String name = route.getRouteInterval().getIntervalName();   // интервал движения
                        switch (name)
                        {
                            case "ежедневно" -> { return true; }    // любой день подходит
                            case "по чётным" -> { return searchDate.getDate() % 2 == 0; }   // только чётный день
                            case "по нечётным" -> { return searchDate.getDate() % 2 != 0; }   // только нечётный день
                            case "по рабочим" -> { return (searchDate.getDay() >= 1 &&  searchDate.getDay() <= 5 ); }   // только пн - пт
                            case "по выходным" -> { return (searchDate.getDay() == 6 || searchDate.getDay() == 0 ); }   // только сб или вс
                            default -> { return false; }
                        }
                    })
                    .collect(Collectors.toList());

            model.addAttribute("routes", routes);

            // формируем дату
            SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd MMMM yyyy");
            model.addAttribute("search_date", outputDateFormat.format(searchDate));
        }
        catch (ParseException e)
        {
            model.addAttribute("date_error", "Указана некорректная дата!");
        }
        return "schedule/show";
    }
}
