package ru.transportcompany.application.models.database;

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
    @ManyToOne
    @JoinColumn(name = "route_start_point")
    private EndPoint routeStartPoint;
    @ManyToOne
    @JoinColumn(name = "route_finish_point")
    private EndPoint routeFinishPoint;
    @Basic
    @Column(name = "route_time")
    private Integer routeTime;
    @ManyToOne
    @JoinColumn(name = "route_interval")
    private RouteInterval routeInterval;
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

    public EndPoint getRouteStartPoint()
    {
        return routeStartPoint;
    }

    public void setRouteStartPoint(EndPoint routeStartPoint)
    {
        this.routeStartPoint = routeStartPoint;
    }

    public EndPoint getRouteFinishPoint()
    {
        return routeFinishPoint;
    }

    public void setRouteFinishPoint(EndPoint routeFinishPoint)
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

    public RouteInterval getRouteInterval()
    {
        return routeInterval;
    }

    public void setRouteInterval(RouteInterval routeInterval)
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
