package ru.transportcompany.application.controllers.database;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.transportcompany.application.models.database.Route;
import ru.transportcompany.application.models.database.Schedule;
import ru.transportcompany.application.models.database.Transport;
import ru.transportcompany.application.repositories.RouteRepository;
import ru.transportcompany.application.repositories.ScheduleRepository;
import ru.transportcompany.application.repositories.TransportRepository;
import ru.transportcompany.application.services.RoutesService;

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
    private RoutesService routesService;
    private RouteRepository routeRepository;
    private TransportRepository transportRepository;

    @GetMapping(value = "/show")
    public String show(Model model)
    {
        model.addAttribute("date", LocalDate.now());
        return "schedule/show";
    }

    @PostMapping(value = "/show")
    public String showDate(@RequestParam String date, Model model)
    {
        List<Schedule> schedules = null;

        try
        {
            // перевод строки в дату
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date searchDate = dateFormat.parse(date);
            model.addAttribute("date", date);

            // формируем дату
            SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd MMMM yyyy");
            model.addAttribute("search_date", outputDateFormat.format(searchDate));

            // маршруты
            schedules = scheduleRepository.findAllByDate(searchDate);
        }
        catch (ParseException e)
        {
            schedules = new ArrayList<>();
            model.addAttribute("date_error", "Указана некорректная дата!");
            e.printStackTrace();
        }
        finally
        {
            model.addAttribute("schedules", schedules);
        }

        return "schedule/show";
    }

    @GetMapping(value = "/add")
    public String addSchedulePage()
    {
        return "schedule/add";
    }

    @PostMapping(value = "/add")
    public String addScheduleItemPage(@RequestParam String date, Model model)
    {
        // маршруты
        List<Route> routes = new ArrayList<>();
        try
        {
            // System.out.println("Дата: " + date);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date searchDate = dateFormat.parse(date);

            model.addAttribute("date", searchDate);

            // System.out.println("Дата: " + searchDate.toString() + " День: " + searchDate.getDate() + " День недели: " + searchDate.getDay());

            // ищем подходящие маршруты на выбранный день
            routes = routesService.getRoutesForDate(searchDate);

            model.addAttribute("routes", routes);

            // формируем дату
            SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd MMMM yyyy");
            model.addAttribute("search_date", outputDateFormat.format(searchDate));
        }
        catch (ParseException e)
        {
            model.addAttribute("date_error", "Указана некорректная дата!");
        }

        // транспорт
        List<Transport> transports = transportRepository.findAll();
        model.addAttribute("transports", transports);

        // строка расписания
        model.addAttribute("schedule", new Schedule());

        return "schedule/add_item";
    }

    @PostMapping(value = "/add/item")
    public String addScheduleItem(@ModelAttribute Schedule schedule, @ModelAttribute("date") Date date)
    {
        schedule.setDate(date);
        scheduleRepository.save(schedule);

        return "redirect:/schedule/show";
    }
}
