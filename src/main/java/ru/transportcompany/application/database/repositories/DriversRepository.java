package ru.transportcompany.application.database.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.transportcompany.application.database.models.Drivers;

public interface DriversRepository extends JpaRepository<Drivers, Long>
{}