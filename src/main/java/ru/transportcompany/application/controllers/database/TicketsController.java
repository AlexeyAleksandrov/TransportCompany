package ru.transportcompany.application.controllers.database;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.transportcompany.application.models.database.Schedule;
import ru.transportcompany.application.models.database.Ticket;
import ru.transportcompany.application.repositories.ScheduleRepository;
import ru.transportcompany.application.repositories.TicketsRepository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/tickets")
@AllArgsConstructor
public class TicketsController
{
    private TicketsRepository ticketsRepository;
    private ScheduleRepository scheduleRepository;

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

        Date nowDate = new Date();   // сегодняшняя дата
        List<Schedule> schedules = scheduleRepository.findAll().stream()
                .filter((schedule -> schedule.getDate().getTime() >= nowDate.getTime()))
                .collect(Collectors.toList());      // выбираем все маршруты, которые доступны

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

        Date nowDate = new Date();   // сегодняшняя дата
        List<Schedule> schedules = scheduleRepository.findAll().stream()
                .filter((schedule -> schedule.getDate().getTime() >= nowDate.getTime()))
                .collect(Collectors.toList());      // выбираем все маршруты, которые доступны

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
}
