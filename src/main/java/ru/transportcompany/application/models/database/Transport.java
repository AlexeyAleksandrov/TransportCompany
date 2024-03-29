package ru.transportcompany.application.models.database;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "transports", schema = "public", catalog = "transport_company")
public class Transport
{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "transport_type")
    private TransportType transportType;
    @Basic
    @Column(name = "gov_number")
    private String govNumber;
    @Basic
    @Column(name = "car_brand")
    private String carBrand;
    @Basic
    @Column(name = "seats_count")
    private Integer seatsCount;
    @OneToOne
    @JoinColumn(name = "driver")
    private Driver driver;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public TransportType getTransportType()
    {
        return transportType;
    }

    public void setTransportType(TransportType transportType)
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

    public Integer getSeatsCount()
    {
        return seatsCount;
    }

    public void setSeatsCount(Integer seatsCount)
    {
        this.seatsCount = seatsCount;
    }

    public Driver getDriver()
    {
        return driver;
    }

    public void setDriver(Driver driver)
    {
        this.driver = driver;
    }


//    public Long getTransportType()
//    {
//        return transportType;
//    }
//
//    public void setTransportType(Long transportType)
//    {
//        this.transportType = transportType;
//    }

//    public Long getDriver()
//    {
//        return driver;
//    }
//
//    public void setDriver(Long driver)
//    {
//        this.driver = driver;
//    }

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
        Transport transport = (Transport) o;
        return Objects.equals(id, transport.id) && Objects.equals(transportType, transport.transportType) && Objects.equals(govNumber, transport.govNumber) && Objects.equals(carBrand, transport.carBrand) && Objects.equals(seatsCount, transport.seatsCount) && Objects.equals(driver, transport.driver);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, transportType, govNumber, carBrand, seatsCount, driver);
    }
}
