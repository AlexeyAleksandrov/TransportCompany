package ru.transportcompany.application.database.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.transportcompany.application.database.models.RouteInterval;

public interface RouteIntervalRepository extends JpaRepository<RouteInterval, Long>
{}