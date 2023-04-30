package ru.transportcompany.application.database.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "route_intervals", schema = "public", catalog = "transport_company")
public class RouteIntervals
{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;
    @Basic
    @Column(name = "interval_name")
    private String intervalName;

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getIntervalName()
    {
        return intervalName;
    }

    public void setIntervalName(String intervalName)
    {
        this.intervalName = intervalName;
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
        RouteIntervals that = (RouteIntervals) o;
        return id == that.id && Objects.equals(intervalName, that.intervalName);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, intervalName);
    }
}
