package ru.transportcompany.application.api.v1.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthResponseModel
{
    private boolean isAuth = false;
    private String userName = "";
}
