package ru.transportcompany.application.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.transportcompany.application.models.AuthRequestModel;
import ru.transportcompany.application.models.AuthResponseModel;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

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
    public String authUser(@ModelAttribute AuthRequestModel requestModel, Model model, HttpServletRequest request)
    {
        if(requestModel.getLogin().length() == 0)  // если не указан логин
        {
            model.addAttribute("UserNotFound", true);
            return "auth/login_new";
        }
        if(requestModel.getPassword().length() == 0)  // если не указан пароль
        {
            model.addAttribute("UserNotFound", true);
            return "auth/login_new";
        }

//        User user = usersRepository.findByUsername(loginForm.getLogin()).orElse(null);
//        if(user == null)    // если пользователя с таким именем нет
//        {
//            model.addAttribute("UserNotFound", true);
//            return "auth/login_new";
//        }

        try
        {
            request.login(requestModel.getLogin(), requestModel.getPassword());  // выполняем принудительную авторизацию
            model.addAttribute("auth_result", new AuthResponseModel(true, "Авторизация прошла успешно!"));
        }
        catch (ServletException e)  // если произошла ошибка
        {
            if(e.getMessage().equals("Неверные учетные данные пользователя"))
            {
//                model.addAttribute("PasswordError", true);
//                return "auth/login_new";
                model.addAttribute("auth_result", new AuthResponseModel(false, "Неверный логин или пароль"));
            }
            else
            {
                e.printStackTrace();
//                return "auth/login_new";
                model.addAttribute("auth_result", new AuthResponseModel(false, "Внутренняя ошибка сервера. Авторизация не выполнена."));
            }
        }

        model.addAttribute("auth_result", new AuthResponseModel(true, "Авторизация прошла успешно!"));
        return "home";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request)
    {
        try
        {
            request.logout();   // выходим из сессии
        }
        catch (ServletException e)
        {
            e.printStackTrace();
        }
        return "redirect:/auth";
    }
}
