package ru.transportcompany.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.transportcompany.application.models.database.Schedule;
import ru.transportcompany.application.models.database.Ticket;

import java.util.List;

public interface TicketsRepository extends JpaRepository<Ticket, Long>
{
    List<Ticket> findBySchedule(Schedule schedule);
    int countBySchedule(Schedule schedule);
}