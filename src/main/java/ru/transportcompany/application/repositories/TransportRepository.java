package ru.transportcompany.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.transportcompany.application.models.database.Transport;

public interface TransportRepository extends JpaRepository<Transport, Long>
{}