package ru.transportcompany.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.transportcompany.application.models.database.Driver;

public interface DriverRepository extends JpaRepository<Driver, Long>
{}