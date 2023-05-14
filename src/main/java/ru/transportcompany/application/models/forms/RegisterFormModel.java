package ru.transportcompany.application.models.forms;

import lombok.Data;

@Data
public class RegisterFormModel
{
    private String username;
    private String password;
    private String password_confirm;
}
