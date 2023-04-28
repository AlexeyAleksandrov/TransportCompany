package ru.transportcompany.application.api.v1;

import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController
{
    @PostMapping(value = "/api/v1/auth")
    public @ResponseBody String authUser(@RequestParam(name = "login") String login,
                                         @RequestParam(name = "password") String password)
    {
        return "Логин: " + login + " Пароль: " + password + " успешно авторизован!";
    }
}
