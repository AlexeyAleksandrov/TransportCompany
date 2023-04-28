package ru.transportcompany.application.api.v1.controllers;

import org.springframework.web.bind.annotation.*;
import ru.transportcompany.application.api.v1.models.AuthRequestModel;
import ru.transportcompany.application.api.v1.models.AuthResponseModel;

@RestController
@RequestMapping("/api/v1")
public class AuthController
{
    @PostMapping(value = "/auth", produces = "application/json")
    public AuthResponseModel authUser(@RequestBody AuthRequestModel requestModel)
    {
        return new AuthResponseModel(true, requestModel.getLogin());
    }
}
