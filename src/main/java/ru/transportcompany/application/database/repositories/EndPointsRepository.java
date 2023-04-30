package ru.transportcompany.application.database.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.transportcompany.application.database.models.EndPoints;

public interface EndPointsRepository extends JpaRepository<EndPoints, Long>
{}