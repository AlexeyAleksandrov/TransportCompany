package ru.transportcompany.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.transportcompany.application.models.database.RouteInterval;

public interface RouteIntervalRepository extends JpaRepository<RouteInterval, Long>
{}