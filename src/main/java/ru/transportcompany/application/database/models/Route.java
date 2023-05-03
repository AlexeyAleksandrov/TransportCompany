package ru.transportcompany.application.database.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "routes", schema = "public", catalog = "transport_company")
public class Route
{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;
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

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
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
        Route route = (Route) o;
        return Objects.equals(id, route.id) && Objects.equals(routeNumber, route.routeNumber) && Objects.equals(routeStartPoint, route.routeStartPoint) && Objects.equals(routeFinishPoint, route.routeFinishPoint) && Objects.equals(routeTime, route.routeTime) && Objects.equals(routeInterval, route.routeInterval) && Objects.equals(costForAdult, route.costForAdult) && Objects.equals(costForChild, route.costForChild);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, routeNumber, routeStartPoint, routeFinishPoint, routeTime, routeInterval, costForAdult, costForChild);
    }
}
