package ru.transportcompany.application.database.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class TransportType
{
    private long id;
    private String name;
    private Integer seatsCount;
}
