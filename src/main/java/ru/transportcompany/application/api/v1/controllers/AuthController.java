package ru.transportcompany.application.api.v1.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.transportcompany.application.api.v1.models.AuthRequestModel;
import ru.transportcompany.application.api.v1.models.AuthResponseModel;

@Controller
//@RequestMapping("/api/v1")
public class AuthController
{
    @GetMapping(value = "/auth")
    public String getAuthPage(Model model)
    {
        model.addAttribute("show_auth", true);
        model.addAttribute("auth_form", new AuthRequestModel());
        return "home";
    }

    @PostMapping(value = "/auth")
    public String authUser(@ModelAttribute AuthRequestModel requestModel, Model model)
    {
        System.out.println("Авторизация: " + requestModel.getLogin() + " " + requestModel.getPassword());
        model.addAttribute("auth_result", new AuthResponseModel(true, "Авторизация прошла успешно!"));
        return "home";
    }
}
