package ru.transportcompany.application.database.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Routes
{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;
    @Basic
    @Column(name = "route_number")
    private String routeNumber;
    @Basic
    @Column(name = "route_start_point")
    private Long routeStartPoint;
    @Basic
    @Column(name = "route_finish_point")
    private Long routeFinishPoint;
    @Basic
    @Column(name = "route_time")
    private Integer routeTime;
    @Basic
    @Column(name = "route_interval")
    private Long routeInterval;
    @Basic
    @Column(name = "cost_for_adult")
    private Integer costForAdult;
    @Basic
    @Column(name = "cost_for_child")
    private Integer costForChild;

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getRouteNumber()
    {
        return routeNumber;
    }

    public void setRouteNumber(String routeNumber)
    {
        this.routeNumber = routeNumber;
    }

    public Long getRouteStartPoint()
    {
        return routeStartPoint;
    }

    public void setRouteStartPoint(Long routeStartPoint)
    {
        this.routeStartPoint = routeStartPoint;
    }

    public Long getRouteFinishPoint()
    {
        return routeFinishPoint;
    }

    public void setRouteFinishPoint(Long routeFinishPoint)
    {
        this.routeFinishPoint = routeFinishPoint;
    }

    public Integer getRouteTime()
    {
        return routeTime;
    }

    public void setRouteTime(Integer routeTime)
    {
        this.routeTime = routeTime;
    }

    public Long getRouteInterval()
    {
        return routeInterval;
    }

    public void setRouteInterval(Long routeInterval)
    {
        this.routeInterval = routeInterval;
    }

    public Integer getCostForAdult()
    {
        return costForAdult;
    }

    public void setCostForAdult(Integer costForAdult)
    {
        this.costForAdult = costForAdult;
    }

    public Integer getCostForChild()
    {
        return costForChild;
    }

    public void setCostForChild(Integer costForChild)
    {
        this.costForChild = costForChild;
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
        Routes routes = (Routes) o;
        return id == routes.id && Objects.equals(routeNumber, routes.routeNumber) && Objects.equals(routeStartPoint, routes.routeStartPoint) && Objects.equals(routeFinishPoint, routes.routeFinishPoint) && Objects.equals(routeTime, routes.routeTime) && Objects.equals(routeInterval, routes.routeInterval) && Objects.equals(costForAdult, routes.costForAdult) && Objects.equals(costForChild, routes.costForChild);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, routeNumber, routeStartPoint, routeFinishPoint, routeTime, routeInterval, costForAdult, costForChild);
    }
}
