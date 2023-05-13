package ru.transportcompany.application.models.users;

import lombok.Data;

@Data
public class User
{
    private String username;
    private String password;
    private Status status;
}
