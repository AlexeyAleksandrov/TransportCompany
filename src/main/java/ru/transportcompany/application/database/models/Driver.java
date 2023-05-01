package ru.transportcompany.application.database.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class Driver
{
    private long id;
    private String firstName;
    private String lastName;
    private String patronymic;
}
