package ru.transportcompany.application.api.v1.controllers;

import lombok.AllArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.transportcompany.application.database.dao.DriversDAO;
import ru.transportcompany.application.database.dao.RouteIntervalsDAO;
import ru.transportcompany.application.database.dao.TransportTypesDAO;
import ru.transportcompany.application.database.models.AddedResult;
import ru.transportcompany.application.database.models.Driver;
import ru.transportcompany.application.database.models.RouteInterval;
import ru.transportcompany.application.database.models.TransportType;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class DataBaseController
{
    private final DriversDAO driversDAO;
    private final TransportTypesDAO transportTypesDAO;
    private final RouteIntervalsDAO routeIntervalsDAO;

    @PostMapping(
            value = "/driver/add",
            produces = "application/json",
            consumes = "application/json")
    public AddedResult addDriver(@RequestBody Driver driver)
    {
        return new AddedResult(driversDAO.addDriver(driver));
    }

    @PostMapping(
            value = "/transport_type/add",
            produces = "application/json",
            consumes = "application/json")
    public AddedResult addTransportType(@RequestBody TransportType transportType)
    {
        return new AddedResult(transportTypesDAO.addTransportType(transportType));
    }

    @PostMapping(
            value = "/route_interval/add",
            produces = "application/json",
            consumes = "application/json")
    public AddedResult addRouteInterval(@RequestBody RouteInterval routeInterval)
    {
        return new AddedResult(routeIntervalsDAO.addRouteInterval(routeInterval));
    }
}
