package ru.transportcompany.application.database.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.transportcompany.application.database.models.RouteIntervals;

public interface RouteIntervalsRepository extends JpaRepository<RouteIntervals, Long>
{}