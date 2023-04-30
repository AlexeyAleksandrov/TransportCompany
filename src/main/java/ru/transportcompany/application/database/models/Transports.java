package ru.transportcompany.application.database.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Transports
{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;
    @Basic
    @Column(name = "transport_type")
    private Long transportType;
    @Basic
    @Column(name = "gov_number")
    private String govNumber;
    @Basic
    @Column(name = "car_brand")
    private String carBrand;
    @Basic
    @Column(name = "driver")
    private Long driver;

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public Long getTransportType()
    {
        return transportType;
    }

    public void setTransportType(Long transportType)
    {
        this.transportType = transportType;
    }

    public String getGovNumber()
    {
        return govNumber;
    }

    public void setGovNumber(String govNumber)
    {
        this.govNumber = govNumber;
    }

    public String getCarBrand()
    {
        return carBrand;
    }

    public void setCarBrand(String carBrand)
    {
        this.carBrand = carBrand;
    }

    public Long getDriver()
    {
        return driver;
    }

    public void setDriver(Long driver)
    {
        this.driver = driver;
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
        Transports that = (Transports) o;
        return id == that.id && Objects.equals(transportType, that.transportType) && Objects.equals(govNumber, that.govNumber) && Objects.equals(carBrand, that.carBrand) && Objects.equals(driver, that.driver);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, transportType, govNumber, carBrand, driver);
    }
}
