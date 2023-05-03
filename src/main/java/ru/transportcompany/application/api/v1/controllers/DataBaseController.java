package ru.transportcompany.application.api.v1.controllers;

import lombok.AllArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.transportcompany.application.database.models.Driver;
import ru.transportcompany.application.database.models.RouteInterval;
import ru.transportcompany.application.database.models.TransportType;
import ru.transportcompany.application.database.repositories.*;

import java.util.List;

@Controller
//@RequestMapping("/api/v1")
@AllArgsConstructor
public class DataBaseController
{
    DriverRepository driverRepository;
    EndPointRepository endPointRepository;
    RouteIntervalRepository routeIntervalRepository;
    RouteRepository routeRepository;
    ScheduleRepository scheduleRepository;
    TransportRepository transportRepository;
    TransportTypeRepository transportTypeRepository;

    @GetMapping("/drivers/add")
    public String getAddDriverPage(Model model)
    {
        model.addAttribute("add_driver", true);
        model.addAttribute("driver", new Driver());
        return "home";
    }

    @PostMapping(value = "/drivers/add")
    public String addDriver(@ModelAttribute Driver driver)
    {
        driverRepository.save(driver);
        return "home";
    }

//
//    @PostMapping(
//            value = "/driver/get/{id}",
//            produces = "application/json",
//            consumes = "application/json")
//    public Driver getDriver(@PathVariable Long id)
//    {
//        return driversDAO.getDriverById(id);
//    }
//
//    @PostMapping(
//            value = "/driver/get_all",
//            produces = "application/json")
//    public List<Driver> getAllDrivers()
//    {
//        return driversDAO.getAll();
//    }
//
//    @PostMapping(
//            value = "/transport_type/add",
//            produces = "application/json",
//            consumes = "application/json")
//    public AddedResult addTransportType(@RequestBody TransportType transportType)
//    {
//        return new AddedResult(transportTypesDAO.addTransportType(transportType));
//    }
//
//    @PostMapping(
//            value = "/route_interval/add",
//            produces = "application/json",
//            consumes = "application/json")
//    public AddedResult addRouteInterval(@RequestBody RouteInterval routeInterval)
//    {
//        return new AddedResult(routeIntervalsDAO.addRouteInterval(routeInterval));
//    }
}
