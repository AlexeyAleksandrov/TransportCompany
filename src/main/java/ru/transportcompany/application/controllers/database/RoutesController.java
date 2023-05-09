package ru.transportcompany.application.controllers.database;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.transportcompany.application.models.database.Route;
import ru.transportcompany.application.repositories.EndPointRepository;
import ru.transportcompany.application.repositories.RouteIntervalRepository;
import ru.transportcompany.application.repositories.RouteRepository;

@Controller
@AllArgsConstructor
@RequestMapping(value = "/routes")
public class RoutesController
{
    RouteRepository routeRepository;
    EndPointRepository endPointRepository;
    RouteIntervalRepository routeIntervalRepository;

    @GetMapping(value = "/show")
    public String getShowRoutes(Model model)
    {
        model.addAttribute("routes", routeRepository.findAll());
        return "routes/show";
    }

    @GetMapping(value = "/add")
    public String getAddRoutePage(Model model)
    {
        model.addAttribute("route", new Route());
        model.addAttribute("end_points", endPointRepository.findAll());
        model.addAttribute("intervals", routeIntervalRepository.findAll());
        return "routes/add";
    }

    @PostMapping(value = "/add")
    public String addRoute(@ModelAttribute Route route)
    {
        routeRepository.save(route);
        return "redirect:/routes/show";
    }

    @GetMapping(value = "/edit")
    public String getEditRoutesPage(Model model)
    {
        model.addAttribute("routes", routeRepository.findAll());
        return "routes/edit";
    }

    @GetMapping(value = "/edit/{id}")
    public String getEditRoute(@PathVariable Long id, Model model)
    {
        model.addAttribute("route", routeRepository.findById(id).orElse(null));
        model.addAttribute("end_points", endPointRepository.findAll());
        model.addAttribute("intervals", routeIntervalRepository.findAll());
        return "routes/edit_item";
    }

    @PostMapping(value = "/edit/{id}")
    public String editRoute(@PathVariable Long id, @ModelAttribute Route route)
    {
        route.setId(id);
        routeRepository.save(route);
        return "redirect:/routes/edit";
    }

    @GetMapping(value = "/delete")
    public String getDeleteRoutesPage(Model model)
    {
        model.addAttribute("routes", routeRepository.findAll());
        return "routes/delete";
    }

    @GetMapping(value = "/delete/{id}")
    public String deleteRoute(@PathVariable Long id)
    {
        routeRepository.deleteById(id);
        return "redirect:/routes/delete";
    }
}
