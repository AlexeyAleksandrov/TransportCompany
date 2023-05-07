package ru.transportcompany.application.controllers.database;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.transportcompany.application.models.database.Transport;
import ru.transportcompany.application.repositories.DriverRepository;
import ru.transportcompany.application.repositories.TransportRepository;
import ru.transportcompany.application.repositories.TransportTypeRepository;

@Controller
@RequestMapping(value = "/transport")
@AllArgsConstructor
public class TransportController
{
    TransportRepository transportRepository;
    TransportTypeRepository transportTypeRepository;
    DriverRepository driverRepository;

    @GetMapping(value = "/show")
    public String getShowPage(Model model)
    {
        model.addAttribute("transports", transportRepository.findAll());
        model.addAttribute("transport_type", transportTypeRepository.findAll());
        model.addAttribute("drivers", driverRepository.findAll());
        return "transports/show";
    }

    @GetMapping(value = "/add")
    public String getAddPage(Model model)
    {
        model.addAttribute("transport", new Transport());
        model.addAttribute("transport_types", transportTypeRepository.findAll());
        model.addAttribute("drivers", driverRepository.findAll());
        return "transports/add";
    }

    @PostMapping(value = "/add")
    public String add(@ModelAttribute Transport transport)
    {
        transportRepository.save(transport);
        return "redirect:/transport/show";
    }

    @GetMapping(value = "/edit")
    public String getEditPage(Model model)
    {
        model.addAttribute("transports", transportRepository.findAll());
        return "transports/edit";
    }

    @GetMapping(value = "/edit/{id}")
    public String getEditById(@PathVariable Long id, Model model)
    {
        model.addAttribute("transport", transportRepository.findById(id).orElse(new Transport()));
        model.addAttribute("transport_types", transportTypeRepository.findAll());
        model.addAttribute("drivers", driverRepository.findAll());
        return "transports/edit_item";
    }

    @PostMapping(value = "/edit/{id}")
    public String edit(@PathVariable Long id, @ModelAttribute Transport transport)
    {
        transport.setId(id);
        transportRepository.save(transport);
        return "redirect:/transport/edit";
    }

    @GetMapping(value = "/delete")
    public String getDeletePage(Model model)
    {
        model.addAttribute("transports", transportRepository.findAll());
        return "transports/delete";
    }

    @GetMapping(value = "/delete/{id}")
    public String deleteById(@PathVariable Long id)
    {
        transportRepository.deleteById(id);
        return "redirect:/transport/delete";
    }
}
