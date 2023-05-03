package ru.transportcompany.application.api.v1.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController
{
    @GetMapping(value = "/")
    public String getHomePage(Model model)
    {
//        model.addAttribute("show_auth", false);
//        model.addAttribute("add_driver", false);
        return "home";
    }
}
