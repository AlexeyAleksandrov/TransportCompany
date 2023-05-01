package ru.transportcompany.application.database.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class Route
{
    private long id;
    private String routeNumber;
    private Long routeStartPoint;
    private Long routeFinishPoint;
    private Integer routeTime;
    private Long routeInterval;
    private Integer costForAdult;
    private Integer costForChild;
}
