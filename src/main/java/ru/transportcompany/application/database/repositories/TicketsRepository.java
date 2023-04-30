package ru.transportcompany.application.database.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.transportcompany.application.database.models.Tickets;

public interface TicketsRepository extends JpaRepository<Tickets, Long>
{}