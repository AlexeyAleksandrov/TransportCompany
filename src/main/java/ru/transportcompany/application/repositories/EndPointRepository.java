package ru.transportcompany.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.transportcompany.application.models.database.EndPoint;

public interface EndPointRepository extends JpaRepository<EndPoint, Long>
{}