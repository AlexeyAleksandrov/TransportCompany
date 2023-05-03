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
//        model.addAttribute("add_driver", false);
        return "home";
    }

    @PostMapping(value = "/auth", produces = "application/json")
    public AuthResponseModel authUser(@RequestBody AuthRequestModel requestModel)
    {
        System.out.println("Авторизация: " + requestModel.getLogin() + " " + requestModel.getPassword());
        return new AuthResponseModel(true, requestModel.getLogin());
    }
}
