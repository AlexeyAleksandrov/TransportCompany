package ru.transportcompany.application.controllers.database;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.transportcompany.application.models.database.EndPoint;
import ru.transportcompany.application.repositories.EndPointRepository;

@Controller
@RequestMapping(value = "/end_points")
@AllArgsConstructor
public class EndPointsController
{
    EndPointRepository endPointRepository;

    @GetMapping(value = "/show")
    public String getShowPage(Model model)
    {
        model.addAttribute("end_points", endPointRepository.findAll());
        return "end_points/show";
    }

    @GetMapping(value = "/add")
    public String getAddEndPointPage(Model model)
    {
        model.addAttribute("end_point", new EndPoint());
        return "end_points/add";
    }

    @PostMapping(value = "/add")
    public String addEndPoint(@ModelAttribute EndPoint EndPoint)
    {
        endPointRepository.save(EndPoint);
        return "redirect:/end_points/show";
    }

    @GetMapping(value = "/edit")
    public String getEditEndPoint(Model model)
    {
        model.addAttribute("end_points", endPointRepository.findAll());
        return "end_points/edit";
    }

    @GetMapping(value = "/edit/{id}")
    public String getEditEndPointById(@PathVariable Long id, Model model)
    {
        model.addAttribute("end_point", endPointRepository.findById(id).orElse(new EndPoint()));
        return "end_points/edit_item";
    }

    @PostMapping(value = "/edit/{id}")
    public String editEndPoint(@PathVariable Long id, @ModelAttribute EndPoint EndPoint)
    {
        EndPoint.setId(id);
        endPointRepository.save(EndPoint);
        return "redirect:/end_points/edit";
    }

    @GetMapping(value = "/delete")
    public String getDeletePage(Model model)
    {
        model.addAttribute("end_points", endPointRepository.findAll());
        return "end_points/delete";
    }

    @GetMapping(value = "/delete/{id}")
    public String deleteById(@PathVariable Long id)
    {
        endPointRepository.deleteById(id);
        return "redirect:/end_points/delete";
    }
}
