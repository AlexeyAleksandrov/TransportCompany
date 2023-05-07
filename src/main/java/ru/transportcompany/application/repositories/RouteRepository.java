package ru.transportcompany.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.transportcompany.application.models.database.Route;

public interface RouteRepository extends JpaRepository<Route, Long>
{}