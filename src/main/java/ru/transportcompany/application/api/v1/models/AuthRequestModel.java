package ru.transportcompany.application.api.v1.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AuthRequestModel
{
    private String login;
    private String password;
}
