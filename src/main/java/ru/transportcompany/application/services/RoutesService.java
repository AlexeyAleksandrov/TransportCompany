package ru.transportcompany.application.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.transportcompany.application.models.database.Route;
import ru.transportcompany.application.repositories.RouteRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RoutesService
{
    RouteRepository routeRepository;

    public List<Route> getRoutesForDate(Date date)
    {
        // ищем подходящие маршруты
        return routeRepository.findAll().stream().filter(route -> {
            switch (route.getRouteInterval().getIntervalName()) // интервал движения
            {
                case "ежедневно" -> {
                    return true;    // любой день подходит
                }
                case "по чётным" -> {
                    return date.getDate() % 2 == 0;     // только чётный день
                }
                case "по нечётным" -> {
                    return date.getDate() % 2 != 0;     // только нечётный день
                }
                case "по рабочим" -> {
                    return (date.getDay() >= 1 && date.getDay() <= 5);      // только пн - пт
                }
                case "по выходным" -> {
                    return (date.getDay() == 6 || date.getDay() == 0);      // только сб или вс
                }
                default -> {
                    return false;
                }
            }
        }).collect(Collectors.toList());
    }
}
