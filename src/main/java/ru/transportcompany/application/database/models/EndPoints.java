package ru.transportcompany.application.database.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "end_points", schema = "public", catalog = "transport_company")
public class EndPoints
{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;
    @Basic
    @Column(name = "point_name")
    private String pointName;

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getPointName()
    {
        return pointName;
    }

    public void setPointName(String pointName)
    {
        this.pointName = pointName;
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
        EndPoints endPoints = (EndPoints) o;
        return id == endPoints.id && Objects.equals(pointName, endPoints.pointName);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, pointName);
    }
}
