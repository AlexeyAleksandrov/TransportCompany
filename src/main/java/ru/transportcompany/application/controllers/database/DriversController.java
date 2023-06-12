package ru.transportcompany.application.controllers.database;

import lombok.AllArgsConstructor;
import org.apache.el.stream.Stream;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.transportcompany.application.models.database.Driver;
import ru.transportcompany.application.models.database.Schedule;
import ru.transportcompany.application.repositories.DriverRepository;
import ru.transportcompany.application.repositories.ScheduleRepository;
import ru.transportcompany.application.services.ScheduleService;

import java.util.Comparator;
import java.util.List;
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
        List<Driver> invalidFlights = driverRepository.findAll().stream()
                .filter(driver -> scheduleRepository.findAll().stream()
                        .map(Schedule::getOnlyDate)
                        .distinct()
                        .anyMatch(date -> scheduleRepository.findAll().stream()
                                .filter(schedule -> schedule.getTransport().getDriver().getId().equals(driver.getId()))
                                .filter(schedule -> schedule.getOnlyDate().equals(date))
                                .count() > 3    // проверяем, что у водителя более 3 рейсов в день
                                || (scheduleRepository.findAll().stream()
                                .filter(schedule -> schedule.getTransport().getDriver().getId().equals(driver.getId()))
                                .filter(schedule -> schedule.getOnlyDate().equals(date))
                                .flatMapToInt(schedule -> IntStream.of(schedule.getRoute().getRouteTime()))
                                .sum() / 60) > 6    // проверяем, что у водителя общее время в пути более 6 часов
                        ))
                .collect(Collectors.toList());

        model.addAttribute("drivers", invalidFlights);

        return "drivers/invalid_flights";
    }
}
