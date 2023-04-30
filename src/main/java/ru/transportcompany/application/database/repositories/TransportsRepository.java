package ru.transportcompany.application.database.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.transportcompany.application.database.models.Transports;

public interface TransportsRepository extends JpaRepository<Transports, Long>
{}