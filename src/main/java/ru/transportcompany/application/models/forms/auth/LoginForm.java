package ru.transportcompany.application.models.forms.auth;

import lombok.Data;

@Data
public class LoginForm
{
    private String login;
    private String password;
}
