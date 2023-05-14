package ru.transportcompany.application.controllers.pages;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.transportcompany.application.models.forms.AuthFormModel;
import ru.transportcompany.application.models.forms.AuthResponseModel;
import ru.transportcompany.application.models.forms.RegisterFormModel;
import ru.transportcompany.application.models.users.User;
import ru.transportcompany.application.repositories.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Controller
@AllArgsConstructor
public class AuthController
{
    UserRepository userRepository;

    // это просто для того, чтобы Spring Security перебрасывал авторизацию на кастомный адрес, где происходит авторизация
    @RequestMapping(value = "/authlogin", method = RequestMethod.GET)
    public String authlogin()
    {
        return "redirect:/auth";
    }

    @GetMapping(value = "/auth")
    public String getAuthPage(Model model)
    {
        model.addAttribute("auth_form", new AuthFormModel());
        return "auth/auth";
    }

    @PostMapping(value = "/auth")
    public String authUser(@ModelAttribute AuthFormModel authForm,
                           Model model,
                           HttpServletRequest request)
    {
        if(authForm.getLogin().length() == 0)  // если не указан логин
        {
            model.addAttribute("auth_result", new AuthResponseModel(false, "Пользователь не найден!"));
        }
        else if(authForm.getPassword().length() == 0)  // если не указан пароль
        {
            model.addAttribute("auth_result", new AuthResponseModel(false, "Пользователь не найден!"));
        }
        else    // если данные введены
        {
            User user = userRepository.findByUsername(authForm.getLogin()).orElse(null);
            if(user == null)    // если пользователя с таким именем нет
            {
                model.addAttribute("auth_result", new AuthResponseModel(false, "Пользователь не найден!"));
            }
            else    // если пользователь найден
            {
                try     // пробуем авторизовать пользователя
                {
                    request.login(authForm.getLogin(), authForm.getPassword());  // выполняем принудительную авторизацию
                    model.addAttribute("auth_result", new AuthResponseModel(true, "Авторизация прошла успешно!"));
                }
                catch (ServletException e)  // если произошла ошибка
                {
                    if(e.getMessage().equals("Неверные учетные данные пользователя"))
                    {
                        model.addAttribute("auth_result", new AuthResponseModel(false, "Неверный логин или пароль"));
                    }
                    else
                    {
                        e.printStackTrace();
                        model.addAttribute("auth_result", new AuthResponseModel(false, "Внутренняя ошибка сервера. Авторизация не выполнена."));
                    }
                }

                model.addAttribute("auth_result", new AuthResponseModel(true, "Авторизация прошла успешно!"));
                return "redirect:/";
            }
        }

        model.addAttribute("auth_form", authForm);      // возвращаем обратно то, что ввёл пользователь

        return "auth/auth";
    }

    @GetMapping(value = "/logout")
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

    @GetMapping(value = "/register")
    public String registerPage(Model model)
    {
        model.addAttribute("form", new RegisterFormModel());
        return "auth/register";
    }

    @PostMapping(value = "/register")
    public String register(@ModelAttribute RegisterFormModel registerForm, Model model, HttpServletRequest request)
    {
        if(registerForm.getUsername().isEmpty()
                || registerForm.getPassword().isEmpty()
                || registerForm.getPassword_confirm().isEmpty())
        {
            model.addAttribute("Error", "Введите корректные данные!");
        }
        else if (registerForm.getUsername().length() < 6 || registerForm.getUsername().length() > 20)
        {
            model.addAttribute("Error", "Имя пользователя должно быть от 6 до 20 символов!");
        }


        return "auth/register";
    }
}
