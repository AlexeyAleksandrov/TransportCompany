package ru.transportcompany.application.api.v1.controllers.database;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.transportcompany.application.database.models.Driver;
import ru.transportcompany.application.database.repositories.DriverRepository;

import java.util.Comparator;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/drivers")
@AllArgsConstructor
public class DriversController
{
    DriverRepository driverRepository;

    @GetMapping("/add")
    public String getAddDriverPage(Model model)
    {
        model.addAttribute("add_driver", true);
        model.addAttribute("driver", new Driver());
        return "home";
    }

    @PostMapping(value = "/add")
    public String addDriver(@ModelAttribute Driver driver)
    {
        driverRepository.save(driver);
        return "home";
    }

    @GetMapping(value = "/get/all")
    public String getAllDrivers(Model model)
    {
        model.addAttribute("show_all_drivers", true);
        model.addAttribute("drivers", driverRepository.findAll().stream().sorted(Comparator.comparingLong(Driver::getId)).collect(Collectors.toList()));
        return "home";
    }

    @GetMapping(value = "/edit")
    public String getEditDrivers(Model model)
    {
        model.addAttribute("edit_drivers", true);
        model.addAttribute("drivers", driverRepository.findAll().stream().sorted(Comparator.comparingLong(Driver::getId)).collect(Collectors.toList()));
        return "home";
    }

    @GetMapping(value = "/edit/{id}")
    public String getEditDriverById(@PathVariable Long id, Model model)
    {
        model.addAttribute("edit_driver_by_id", true);
        model.addAttribute("driver", driverRepository.findById(id).orElse(new Driver()));
        return "home";
    }

    @PostMapping(value = "/edit/{id}")
    public String editDriverById(@PathVariable Long id, @ModelAttribute Driver driver)
    {
        System.out.println("edit id = " + driver.getId());
        driver.setId(id);
        driverRepository.save(driver);
        return "redirect:/drivers/edit";
    }
}
