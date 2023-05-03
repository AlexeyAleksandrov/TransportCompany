package ru.transportcompany.application.database.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.transportcompany.application.database.models.Transport;

public interface TransportRepository extends JpaRepository<Transport, Long>
{}