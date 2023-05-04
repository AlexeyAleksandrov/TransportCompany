package ru.transportcompany.application.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.transportcompany.application.models.AuthRequestModel;
import ru.transportcompany.application.models.AuthResponseModel;

@Controller
public class AuthController
{
    @GetMapping(value = "/auth")
    public String getAuthPage(Model model)
    {
        model.addAttribute("auth_form", new AuthRequestModel());
        return "auth/auth";
    }

    @PostMapping(value = "/auth")
    public String authUser(@ModelAttribute AuthRequestModel requestModel, Model model)
    {
        System.out.println("Авторизация: " + requestModel.getLogin() + " " + requestModel.getPassword());
        model.addAttribute("auth_result", new AuthResponseModel(true, "Авторизация прошла успешно!"));
        return "home";
    }
}
