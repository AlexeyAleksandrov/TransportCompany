package ru.transportcompany.application.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthResponseModel
{
    private boolean isAuth = false;
    private String authResult = "";
}
