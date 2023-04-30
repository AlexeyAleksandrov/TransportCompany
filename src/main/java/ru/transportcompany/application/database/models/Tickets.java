package ru.transportcompany.application.database.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Tickets
{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;
    @Basic
    @Column(name = "schedule")
    private Long schedule;
    @Basic
    @Column(name = "children")
    private Boolean children;
    @Basic
    @Column(name = "passenger_name")
    private String passengerName;

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public Long getSchedule()
    {
        return schedule;
    }

    public void setSchedule(Long schedule)
    {
        this.schedule = schedule;
    }

    public Boolean getChildren()
    {
        return children;
    }

    public void setChildren(Boolean children)
    {
        this.children = children;
    }

    public String getPassengerName()
    {
        return passengerName;
    }

    public void setPassengerName(String passengerName)
    {
        this.passengerName = passengerName;
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
        Tickets tickets = (Tickets) o;
        return id == tickets.id && Objects.equals(schedule, tickets.schedule) && Objects.equals(children, tickets.children) && Objects.equals(passengerName, tickets.passengerName);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, schedule, children, passengerName);
    }
}
