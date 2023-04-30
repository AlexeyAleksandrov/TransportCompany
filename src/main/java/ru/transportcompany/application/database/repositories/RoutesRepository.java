package ru.transportcompany.application.database.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.transportcompany.application.database.models.Routes;

public interface RoutesRepository extends JpaRepository<Routes, Long>
{}