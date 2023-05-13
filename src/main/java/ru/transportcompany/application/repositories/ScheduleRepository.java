package ru.transportcompany.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.transportcompany.application.models.database.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long>
{}