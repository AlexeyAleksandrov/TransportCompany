package ru.transportcompany.application.database.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.transportcompany.application.database.models.Driver;

public interface DriverRepository extends JpaRepository<Driver, Long>
{}