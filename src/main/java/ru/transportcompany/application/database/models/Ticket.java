package ru.transportcompany.application.database.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class Ticket
{
    private long id;
    private Long schedule;
    private Boolean children;
    private String passengerName;
}
