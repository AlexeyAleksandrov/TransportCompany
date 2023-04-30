package ru.transportcompany.application.database.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.transportcompany.application.database.models.TransportTypes;

public interface TransportTypesRepository extends JpaRepository<TransportTypes, Long>
{}