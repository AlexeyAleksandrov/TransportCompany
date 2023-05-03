package ru.transportcompany.application.database.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "route_intervals", schema = "public", catalog = "transport_company")
public class RouteInterval
{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;
    @Basic
    @Column(name = "interval_name")
    private String intervalName;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
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
        RouteInterval that = (RouteInterval) o;
        return Objects.equals(id, that.id) && Objects.equals(intervalName, that.intervalName);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, intervalName);
    }
}
