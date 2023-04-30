package ru.transportcompany.application.database.models;

import javax.persistence.*;
import java.sql.Time;
import java.util.Objects;

@Entity
public class Schedule
{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;
    @Basic
    @Column(name = "point")
    private Long point;
    @Basic
    @Column(name = "route")
    private Long route;
    @Basic
    @Column(name = "time_start")
    private Time timeStart;
    @Basic
    @Column(name = "transport")
    private Long transport;

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public Long getPoint()
    {
        return point;
    }

    public void setPoint(Long point)
    {
        this.point = point;
    }

    public Long getRoute()
    {
        return route;
    }

    public void setRoute(Long route)
    {
        this.route = route;
    }

    public Time getTimeStart()
    {
        return timeStart;
    }

    public void setTimeStart(Time timeStart)
    {
        this.timeStart = timeStart;
    }

    public Long getTransport()
    {
        return transport;
    }

    public void setTransport(Long transport)
    {
        this.transport = transport;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        Schedule schedule = (Schedule) o;
        return id == schedule.id && Objects.equals(point, schedule.point) && Objects.equals(route, schedule.route) && Objects.equals(timeStart, schedule.timeStart) && Objects.equals(transport, schedule.transport);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, point, route, timeStart, transport);
    }
}
