package ru.transportcompany.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.transportcompany.application.controllers.database.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long>
{}