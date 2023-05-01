package ru.transportcompany.application.database.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class RouteInterval
{
    private long id;
    private String intervalName;
}
