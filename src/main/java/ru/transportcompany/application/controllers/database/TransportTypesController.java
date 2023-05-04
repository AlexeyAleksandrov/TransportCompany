package ru.transportcompany.application.controllers.database;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.transportcompany.application.database.models.TransportType;
import ru.transportcompany.application.database.repositories.TransportTypeRepository;

@Controller
@RequestMapping(value = "/transport_types")
@AllArgsConstructor
public class TransportTypesController
{
    TransportTypeRepository transportTypeRepository;

    @GetMapping(value = "/show")
    public String getShowPage(Model model)
    {
        model.addAttribute("showTransportTypes", true);
        model.addAttribute("transport_types", transportTypeRepository.findAll());
        return "home";
    }

    @GetMapping(value = "/add")
    public String getAddTransportTypePage(Model model)
    {
        model.addAttribute("addTransportType", true);
        model.addAttribute("transportType", new TransportType());
        return "home";
    }

    @PostMapping(value = "/add")
    public String addTransportType(@ModelAttribute TransportType transportType)
    {
        transportTypeRepository.save(transportType);
        return "redirect:/transport_types/show";
    }

    @GetMapping(value = "/edit")
    public String getEditTransportType(Model model)
    {
        model.addAttribute("editTransportTypes", true);
        model.addAttribute("transport_types", transportTypeRepository.findAll());
        return "home";
    }

    @GetMapping(value = "/edit/{id}")
    public String getEditTransportTypeById(@PathVariable Long id, Model model)
    {
        model.addAttribute("editTransportTypeById", true);
        model.addAttribute("transport_type", transportTypeRepository.findById(id).orElse(new TransportType()));
        return "home";
    }

    @PostMapping(value = "/edit/{id}")
    public String editTransportType(@PathVariable Long id, @ModelAttribute TransportType transportType)
    {
        transportType.setId(id);
        transportTypeRepository.save(transportType);
        return "redirect:/transport_types/edit";
    }

    @GetMapping(value = "/delete")
    public String getDeletePage(Model model)
    {
        model.addAttribute("deleteTransportType", true);
        model.addAttribute("transport_types", transportTypeRepository.findAll());
        return "home";
    }

    @GetMapping(value = "/delete/{id}")
    public String deleteById(@PathVariable Long id)
    {
        transportTypeRepository.deleteById(id);
        return "redirect:/transport_types/delete";
    }
}
