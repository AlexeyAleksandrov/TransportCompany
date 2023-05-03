package ru.transportcompany.application.database.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.transportcompany.application.database.models.EndPoint;

public interface EndPointRepository extends JpaRepository<EndPoint, Long>
{}