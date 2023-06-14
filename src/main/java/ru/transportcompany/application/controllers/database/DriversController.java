package ru.transportcompany.application.controllers.database;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.transportcompany.application.models.database.Driver;
import ru.transportcompany.application.models.database.EndPoint;
import ru.transportcompany.application.models.database.Schedule;
import ru.transportcompany.application.repositories.DriverRepository;
import ru.transportcompany.application.repositories.ScheduleRepository;
import ru.transportcompany.application.services.ScheduleService;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping(value = "/drivers")
@AllArgsConstructor
public class DriversController
{
    DriverRepository driverRepository;
    ScheduleService scheduleService;
    ScheduleRepository scheduleRepository;

    @GetMapping("/add")
    public String getAddDriverPage(Model model)
    {
        model.addAttribute("driver", new Driver());
        return "drivers/add";
    }

    @PostMapping(value = "/add")
    public String addDriver(@ModelAttribute Driver driver)
    {
        driverRepository.save(driver);
        return "redirect:/drivers/show";
    }

    @GetMapping(value = "/show")
    public String getAllDrivers(Model model)
    {
        model.addAttribute("drivers", driverRepository.findAll().stream().sorted(Comparator.comparingLong(Driver::getId)).collect(Collectors.toList()));
        return "drivers/show";
    }

    @GetMapping(value = "/edit")
    public String getEditDrivers(Model model)
    {
        model.addAttribute("drivers", driverRepository.findAll().stream().sorted(Comparator.comparingLong(Driver::getId)).collect(Collectors.toList()));
        return "drivers/edit";
    }

    @GetMapping(value = "/edit/{id}")
    public String getEditDriverById(@PathVariable Long id, Model model)
    {
        model.addAttribute("driver", driverRepository.findById(id).orElse(new Driver()));
        return "drivers/edit_item";
    }

    @PostMapping(value = "/edit/{id}")
    public String editDriverById(@PathVariable Long id, @ModelAttribute Driver driver)
    {
        driver.setId(id);
        driverRepository.save(driver);
        return "redirect:/drivers/edit";
    }

    @GetMapping(value = "/delete")
    public String getDeleteDrivers(Model model)
    {
        model.addAttribute("drivers", driverRepository.findAll().stream().sorted(Comparator.comparingLong(Driver::getId)).collect(Collectors.toList()));
        return "drivers/delete";
    }

    @GetMapping(value = "/delete/{id}")
    public String deleteDriverById(@PathVariable Long id)
    {
        driverRepository.deleteById(id);
        return "redirect:/drivers/delete";
    }

    @GetMapping("/schedule/{id}")
    public String schedule(@PathVariable Long id, Model model)
    {
        Driver driver = driverRepository.getById(id);
        List<Schedule> schedules = scheduleService.getSchedulesForDriverOnMonth(driver);
        model.addAttribute("driver", driver);
        model.addAttribute("schedules", schedules);
        return "drivers/schedule";
    }

    @GetMapping("/checkInvalidFlights")
    public String checkInvalidFlights(Model model)
    {
        List<Driver> invalidFlightsBySummaryTime = driverRepository.findAll().stream()       // для каждого водителя
                .filter(driver -> scheduleRepository.findAll().stream()     // составляем список дней
                        .map(Schedule::getOnlyDate)
                        .distinct()
                        .anyMatch(date -> (scheduleRepository.findAll().stream()
                                .filter(schedule -> schedule.getTransport().getDriver().getId().equals(driver.getId()))
                                .filter(schedule -> schedule.getOnlyDate().equals(date))
                                .flatMapToInt(schedule -> IntStream.of(schedule.getRoute().getRouteTime()))
                                .sum() / 60) > 6    // проверяем, что у водителя общее время в пути более 6 часов
                        ))
                .toList();

        List<Driver> invalidFlightsByFlightsCountInDay= driverRepository.findAll().stream()       // для каждого водителя
                .filter(driver -> scheduleRepository.findAll().stream()     // составляем список дней
                        .map(Schedule::getOnlyDate)
                        .distinct()
                        .anyMatch(date -> scheduleRepository.findAll().stream()
                                .filter(schedule -> schedule.getTransport().getDriver().getId().equals(driver.getId()))
                                .filter(schedule -> schedule.getOnlyDate().equals(date))
                                .count() > 3    // проверяем, что у водителя более 3 рейсов в день
                        ))
                .toList();

        List<Driver> invalidFlightsBySchedule = driverRepository.findAll().stream()       // для каждого водителя
                .filter(driver -> scheduleRepository.findAll().stream()     // составляем список дней
                        .map(Schedule::getOnlyDate)     // конвертируем стрим расписания в стрим дней
                        .distinct()     // удаляем дубликаты дней
                        .anyMatch(date -> scheduleRepository.findAll().stream()     // для каждого дня, в который работает данный водитель берём строку расписания для него
                                .filter(schedule -> schedule.getTransport().getDriver().getId().equals(driver.getId()) && schedule.getOnlyDate().equals(date))      // оставляем только те записи, которые относятся к данному водителю и на текущий день
                                .anyMatch(schedule -> scheduleRepository.findAll().stream()     // для каждой записи ищем другую запись, время между стартом и финишем для которых будет менее 1 часа
                                        .filter(scheduleCompare -> scheduleCompare.getTransport().getDriver().getId().equals(schedule.getTransport().getDriver().getId()) && scheduleCompare.getOnlyDate().equals(schedule.getOnlyDate()))    // получаем только маршруты в искомый день для искомого водителя
                                        .filter(scheduleCompare -> !scheduleCompare.getId().equals(schedule.getId()))   // исключаем повторения, чтобы не сравнивать с самим собой
                                        .anyMatch(scheduleCompare -> Math.abs(Duration.between(scheduleCompare.getTimeStart(), schedule.getTimeEnd()).toMinutes()) < 60     // время между началом и концом меньше 60 минут
                                        )
                                )
                        )
                )
                .distinct()     // убираем дубликаты водителей
                .toList();

        List<Driver> invalidFlightsByStartAndEndPoints = driverRepository.findAll().stream()       // для каждого водителя
                .filter(driver -> scheduleRepository.findAll().stream()     // составляем список дней
                        .map(Schedule::getOnlyDate)     // конвертируем стрим расписания в стрим дней
                        .distinct()     // удаляем дубликаты дней
                        .anyMatch(date -> scheduleRepository.findAll().stream()     // для каждого дня, в который работает данный водитель берём строку расписания для него
                                .filter(schedule -> schedule.getTransport().getDriver().getId().equals(driver.getId()) && schedule.getOnlyDate().equals(date))      // оставляем только те записи, которые относятся к данному водителю и на текущий день
                                .anyMatch(schedule -> {
                                        Optional<Schedule> min = scheduleRepository.findAll().stream()     // для каждой записи ищем другую запись, время между стартом и финишем для которых будет менее 1 часа
                                                .filter(scheduleCompare -> scheduleCompare.getTransport().getDriver().getId().equals(schedule.getTransport().getDriver().getId()) && scheduleCompare.getOnlyDate().equals(schedule.getOnlyDate()))    // получаем только маршруты в искомый день для искомого водителя
                                                .filter(scheduleCompare -> !scheduleCompare.getId().equals(schedule.getId()))   // исключаем повторения, чтобы не сравнивать с самим собой
                                                .filter(scheduleCompare -> schedule.getTimeStart().isBefore(scheduleCompare.getTimeStart()))    // исключаем маршруты, раньше заданного срока
                                                .min(Comparator.comparing(Schedule::getTimeStart));
                                        if(min.isPresent())
                                        {
                                            EndPoint lastEndPoint = schedule.getDirection() == 0 ? schedule.getRoute().getRouteFinishPoint() : schedule.getRoute().getRouteStartPoint();
                                            EndPoint nextStartPoint = min.get().getDirection() == 0 ? min.get().getRoute().getRouteStartPoint() : min.get().getRoute().getRouteFinishPoint();
                                            // если конечный и начальный пункты не совпадают
                                            return !lastEndPoint.getId().equals(nextStartPoint.getId());
                                        }
                                        return false;
                                })
                        )
                )
                .distinct()     // убираем дубликаты водителей
                .toList();

        List<Driver> invalidFlightsInDay = new ArrayList<>();   // финальный список

        invalidFlightsInDay.addAll(invalidFlightsBySummaryTime);    // общее время в пути более 6 часов
        invalidFlightsInDay.addAll(invalidFlightsByFlightsCountInDay);  // более 3 рейсов в день
        invalidFlightsInDay.addAll(invalidFlightsBySchedule);       // объединяем 2 списка
        invalidFlightsInDay.addAll(invalidFlightsByStartAndEndPoints);  // конечный пункт предыдущего и начальный пункт следующего маршрута не совпадают

        invalidFlightsInDay = invalidFlightsInDay.stream().distinct().toList();     // удаляем дубликаты

        model.addAttribute("drivers", invalidFlightsInDay);

        return "drivers/invalid_flights";
    }
}
