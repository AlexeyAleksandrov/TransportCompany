package ru.transportcompany.application.database.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.transportcompany.application.database.models.TransportType;

public interface TransportTypeRepository extends JpaRepository<TransportType, Long>
{}