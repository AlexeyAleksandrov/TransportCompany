package ru.transportcompany.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.transportcompany.application.models.database.Ticket;

public interface TicketsRepository extends JpaRepository<Ticket, Long>
{}