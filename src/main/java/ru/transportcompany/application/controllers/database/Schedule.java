package ru.transportcompany.application.controllers.database;

import ru.transportcompany.application.models.database.Route;
import ru.transportcompany.application.models.database.Transport;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "schedule")
public class Schedule
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route")
    private Route route;

    @Column(name = "time_start")
    private LocalTime timeStart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transport")
    private Transport transport;

    public Transport getTransport() {return transport;}

    public void setTransport(Transport transport) {this.transport = transport;}

    public LocalTime getTimeStart() {return timeStart;}

    public void setTimeStart(LocalTime timeStart) {this.timeStart = timeStart;}

    public Route getRoute() {return route;}

    public void setRoute(Route route) {this.route = route;}

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}
}