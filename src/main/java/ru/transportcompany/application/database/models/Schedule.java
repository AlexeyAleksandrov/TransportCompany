package ru.transportcompany.application.database.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Time;

@Data
@EqualsAndHashCode
public class Schedule
{
    private long id;
    private Long point;
    private Long route;
    private Time timeStart;
    private Long transport;
}
