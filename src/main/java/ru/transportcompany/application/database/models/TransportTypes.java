package ru.transportcompany.application.database.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "transport_types", schema = "public", catalog = "transport_company")
public class TransportTypes
{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "seats_count")
    private Integer seatsCount;

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Integer getSeatsCount()
    {
        return seatsCount;
    }

    public void setSeatsCount(Integer seatsCount)
    {
        this.seatsCount = seatsCount;
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
        TransportTypes that = (TransportTypes) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(seatsCount, that.seatsCount);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, name, seatsCount);
    }
}
