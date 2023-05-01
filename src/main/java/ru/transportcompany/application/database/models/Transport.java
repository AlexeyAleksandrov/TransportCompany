package ru.transportcompany.application.database.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class Transport
{

    private long id;
    private Long transportType;
    private String govNumber;
    private String carBrand;
    private Long driver;
}
