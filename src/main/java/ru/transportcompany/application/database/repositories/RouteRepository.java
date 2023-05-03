package ru.transportcompany.application.database.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.transportcompany.application.database.models.Route;

public interface RouteRepository extends JpaRepository<Route, Long>
{}