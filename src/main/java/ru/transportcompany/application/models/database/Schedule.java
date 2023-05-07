package ru.transportcompany.application.models.database;

import javax.persistence.*;
import java.sql.Time;
import java.util.Objects;

@Entity
public class Schedule
{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;
    @Basic
    @Column(name = "point")
    private Long point;
    @Basic
    @Column(name = "route")
    private Long route;
    @Basic
    @Column(name = "time_start")
    private Time timeStart;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
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
        return Objects.equals(id, schedule.id) && Objects.equals(point, schedule.point) && Objects.equals(route, schedule.route) && Objects.equals(timeStart, schedule.timeStart);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, point, route, timeStart);
    }
}
