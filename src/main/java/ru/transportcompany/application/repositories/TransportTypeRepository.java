package ru.transportcompany.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.transportcompany.application.models.database.TransportType;

public interface TransportTypeRepository extends JpaRepository<TransportType, Long>
{}