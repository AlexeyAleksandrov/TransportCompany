package ru.transportcompany.application.database.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.transportcompany.application.database.models.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long>
{}