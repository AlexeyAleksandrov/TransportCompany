package ru.transportcompany.application.models.database;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "end_points", schema = "public", catalog = "transport_company")
public class EndPoint
{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;
    @Basic
    @Column(name = "point_name")
    private String pointName;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
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
        EndPoint endPoint = (EndPoint) o;
        return Objects.equals(id, endPoint.id) && Objects.equals(pointName, endPoint.pointName);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, pointName);
    }
}
