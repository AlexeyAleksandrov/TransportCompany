package ru.transportcompany.application.api.v1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.transportcompany.application.api.v1.models.AuthRequestModel;
import ru.transportcompany.application.api.v1.models.AuthResponseModel;
import ru.transportcompany.application.database.models.TransportTypes;
import ru.transportcompany.application.database.repositories.TransportTypesRepository;

@RestController
@RequestMapping("/api/v1")
public class AuthController
{
    @Autowired
    private TransportTypesRepository transportTypesRepository;

    @PostMapping(value = "/auth", produces = "application/json")
    public AuthResponseModel authUser(@RequestBody AuthRequestModel requestModel)
    {
        TransportTypes transportType = new TransportTypes();

        return new AuthResponseModel(true, requestModel.getLogin());
    }
}
