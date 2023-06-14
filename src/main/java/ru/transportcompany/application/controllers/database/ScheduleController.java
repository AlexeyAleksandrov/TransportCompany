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
import ru.transportcompany.application.services.ScheduleService;

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
    private TransportRepository transportRepository;
    private ScheduleService scheduleService;

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
            schedules.sort((a, b) -> a.getRoute().getRouteNumber().equals(b.getRoute().getRouteNumber()) ? a.getTimeStart().compareTo(b.getTimeStart()) : a.getRoute().getRouteNumber().compareTo(b.getRoute().getRouteNumber()));  // сортируем по маршрутам и времени отправления

            // маршруты по которым нет рейсов
            List<Route> routesForDay = routesService.getRoutesForDate(searchDate);
            List<Schedule> finalSchedules = schedules;
            List<Route> notFlightRoutesForDay = routesForDay.stream()
                    .filter(route -> finalSchedules.stream()
                            .noneMatch(schedule -> schedule.getRoute().getId()
                                    .equals(route.getId())))
                    .collect(Collectors.toList());      // ищем маршруты, которые должны быть в этот день, но на них отсутствуют рейсы
            model.addAttribute("not_flight_routes", notFlightRoutesForDay);
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
            model.addAttribute("schedule_service", scheduleService);
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
        model.addAttribute("add_date", date);
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

    @PostMapping(value = "/add/item/{add_date}")
    public String addScheduleItem(@ModelAttribute Schedule schedule, @PathVariable String add_date)
    {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try
        {
            date = dateFormat.parse(add_date);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

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

    @GetMapping(value = "/edit/{id}")
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
        // id
        schedule.setId(id);

        // дата
        Date date = scheduleRepository.findById(id).orElseThrow().getDate();
        schedule.setDate(date);

        //сохраняем
        scheduleRepository.save(schedule);

        return "redirect:/schedule/edit/";
    }

    @GetMapping(value = "/delete")
    public String deleteSchedulePage()
    {
        return "schedule/delete";
    }

    @PostMapping(value = "/delete")
    public String deleteScheduleChose(@RequestParam String date, Model model)
    {
        Date searchDate = new Date();

        try
        {
            // перевод строки в дату
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            searchDate = dateFormat.parse(date);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
            model.addAttribute("date_error", "Указана некорректная дата!");
            e.printStackTrace();
        }
        finally
        {
            // поиск расписания на дату
            List<Schedule> schedules = scheduleRepository.findAllByDate(searchDate);

            // формируем дату
            SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd MMMM yyyy");
            model.addAttribute("search_date", outputDateFormat.format(searchDate));

            // дата в поиске
            model.addAttribute("date", date);
            model.addAttribute("schedules", schedules);
        }

        return "schedule/delete_chose";
    }

    @GetMapping(value = "/delete/{id}")
    public String deleteScheduleItem(@PathVariable Long id)
    {
        scheduleRepository.deleteById(id);
        return "redirect:/schedule/delete";
    }
}
