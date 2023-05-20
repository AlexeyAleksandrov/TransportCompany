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
        List<Route> routes = null;
        try
        {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date searchDate = dateFormat.parse(date);

            model.addAttribute("date", searchDate);

            // ищем подходящие маршруты на выбранный день
            routes = routesService.getRoutesForDate(searchDate);

            // формируем дату
            SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd MMMM yyyy");
            model.addAttribute("search_date", outputDateFormat.format(searchDate));
        }
        catch (ParseException e)
        {
            routes = new ArrayList<>();
            model.addAttribute("date_error", "Указана некорректная дата!");
        }
        finally
        {
            model.addAttribute("routes", routes);
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

    @GetMapping(value = "/edit")
    public String editSchedulePage(Model model)
    {
        model.addAttribute("date", LocalDate.now());
        return "schedule/edit";
    }

    @PostMapping(value = "/edit")
    public String editScheduleChosePage(@RequestParam String date, Model model)
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

            // расписание
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

        return "schedule/edit_chose";
    }

    @PostMapping(value = "/edit/{id}")
    public String editScheduleItemPage(@PathVariable Long id, @ModelAttribute("date") Date date, Model model)
    {
        // формируем дату
        SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd MMMM yyyy");
        model.addAttribute("search_date", outputDateFormat.format(date));

        // расписание
        Schedule schedule = scheduleRepository.findById(id).orElse(null);
        model.addAttribute("schedule", schedule);

        // ищем подходящие маршруты на выбранный день
        List<Route> routes = routesService.getRoutesForDate(date);
        model.addAttribute("routes", routes);

        // транспорт
        List<Transport> transports = transportRepository.findAll();
        model.addAttribute("transports", transports);

        return "schedule/edit_item";
    }

    @PostMapping(value = "/edit/item/{id}")
    public String editScheduleItem(@PathVariable Long id, @ModelAttribute Schedule schedule)
    {
        schedule.setId(id);
        scheduleRepository.save(schedule);
        return "redirect:/schedule/edit/";
    }
}
