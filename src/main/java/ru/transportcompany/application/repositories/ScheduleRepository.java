package ru.transportcompany.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.transportcompany.application.models.database.Schedule;

import java.util.Date;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long>
{
    List<Schedule> findAllByDate(Date date);
}