package ru.transportcompany.application.controllers.database;

import lombok.AllArgsConstructor;
import org.hibernate.type.LocalDateTimeType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.transportcompany.application.models.database.Schedule;
import ru.transportcompany.application.models.database.Ticket;
import ru.transportcompany.application.repositories.ScheduleRepository;
import ru.transportcompany.application.repositories.TicketsRepository;
import ru.transportcompany.application.services.ScheduleService;

import java.util.List;

@Controller
@RequestMapping("/tickets")
@AllArgsConstructor
public class TicketsController
{
    private TicketsRepository ticketsRepository;
    private ScheduleRepository scheduleRepository;
    private ScheduleService scheduleService;

    @GetMapping("/show")
    public String showPage(Model model)
    {
        List<Ticket> tickets = ticketsRepository.findAll();
        model.addAttribute("tickets", tickets);
        return "tickets/show";
    }

    @GetMapping("/add")
    public String addPage(Model model)
    {
        model.addAttribute("ticket", new Ticket());

        List<Schedule> schedules = scheduleService.getActualSchedules();      // выбираем все маршруты, которые доступны

        model.addAttribute("schedules", schedules);
        return "tickets/add";
    }

    @PostMapping("/add")
    public String addTicket(@ModelAttribute Ticket ticket)
    {
        ticketsRepository.save(ticket);
        return "redirect:/tickets/show";
    }

    @GetMapping("/edit")
    public String editTicketPage(Model model)
    {
        List<Ticket> tickets = ticketsRepository.findAll();
        model.addAttribute("tickets", tickets);
        return "tickets/edit";
    }

    @GetMapping("/edit/{id}")
    public String editTicketItemPage(@PathVariable Long id, Model model)
    {
        Ticket ticket = ticketsRepository.findById(id).orElseThrow();
        model.addAttribute("ticket", ticket);

        List<Schedule> schedules = scheduleService.getActualSchedules();      // выбираем все маршруты, которые доступны

        model.addAttribute("schedules", schedules);
        return "tickets/edit_item";
    }

    @PostMapping("/edit/{id}")
    public String editTicketItem(@PathVariable Long id, @ModelAttribute("ticket") Ticket ticket)
    {
        ticket.setId(id);
        ticketsRepository.save(ticket);
        return "redirect:/tickets/edit";
    }

    @GetMapping("/delete")
    public String deleteTicketPage(Model model)
    {
        List<Ticket> tickets = ticketsRepository.findAll();
        model.addAttribute("tickets", tickets);
        return "tickets/delete";
    }

    @GetMapping("/delete/{id}")
    public String deleteTicketById(@PathVariable Long id)
    {
        ticketsRepository.deleteById(id);
        return "redirect:/tickets/delete";
    }
}
