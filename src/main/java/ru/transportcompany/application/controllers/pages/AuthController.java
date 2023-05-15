package ru.transportcompany.application.controllers.pages;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.transportcompany.application.models.forms.auth.LoginForm;
import ru.transportcompany.application.models.forms.auth.SignUpUserForm;
import ru.transportcompany.application.models.users.User;
import ru.transportcompany.application.repositories.UsersRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Controller
@AllArgsConstructor
public class AuthController
{
    private final UsersRepository usersRepository;  // репозиторий для работы с пользователями
    private final PasswordEncoder passwordEncoder;  // кодировщик паролей

    // это просто для того, чтобы Spring Security перебрасывал авторизацию на кастомный адрес, где происходит авторизация
    @RequestMapping(value = "/authlogin", method = RequestMethod.GET)
    public String authlogin()
    {
        return "redirect:/auth";
    }

    @RequestMapping(value = "/auth", method = RequestMethod.GET)
    public String loginPage(Model model)
    {
        model.addAttribute("form", new LoginForm());
        return "auth/auth";
    }

    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public String login(@ModelAttribute("form") LoginForm loginForm, Model model, HttpServletRequest request)
    {
        if(loginForm.getLogin().length() == 0)  // если не указан логин
        {
            model.addAttribute("UserNotFound", true);
            return "auth/auth";
        }
        if(loginForm.getPassword().length() == 0)  // если не указан пароль
        {
            model.addAttribute("UserNotFound", true);
            return "auth/auth";
        }

        User user = usersRepository.findByUsername(loginForm.getLogin()).orElse(null);
        if(user == null)    // если пользователя с таким именем нет
        {
            model.addAttribute("UserNotFound", true);
            return "auth/auth";
        }

        try
        {
            request.login(loginForm.getLogin(), loginForm.getPassword());  // выполняем принудительную авторизацию
            return "redirect:/";
        }
        catch (ServletException e)  // если произошла ошибка
        {
            if(e.getMessage().equals("Неверные учетные данные пользователя"))
            {
                model.addAttribute("PasswordError", true);
                return "auth/auth";
            }
            else
            {
                e.printStackTrace();
                return "auth/auth";
            }
        }
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
        return "redirect:/login";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registration(Model model)
    {
        model.addAttribute("registerForm", new SignUpUserForm());
        return "auth/register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String signUp(@ModelAttribute("form") SignUpUserForm signUpUserForm, Model model, HttpServletRequest request)
    {
        boolean error = false;  // флаг ошибки

        if(signUpUserForm.getUsername().length() < 6 || signUpUserForm.getUsername().length() > 20) // проверка на длину логина
        {
            model.addAttribute("loginError", "Имя пользователя должно быть от 6 до 20 символов!");
            error = true;
        }

        if(signUpUserForm.getPassword().length() < 6 || signUpUserForm.getPassword().length() > 20) // проверка на длину пароля
        {
            model.addAttribute("passwordError", "Пароль должен содержать от 6 до 20 символов");
            error = true;
        }

        if(!signUpUserForm.getPassword().equals(signUpUserForm.getConfirm_password()))  // если пароли не совпадают
        {
            model.addAttribute("passwordNotConfirm", true);
            error = true;
        }

        if(error)
        {
            model.addAttribute("registerForm", signUpUserForm);
            return "auth/register";
        }
        else
        {
            if(usersRepository.existsByUsername(signUpUserForm.getUsername()))  // проверяем, что пользователь уже существует
            {
                model.addAttribute("userExists", true);
                model.addAttribute("registerForm", signUpUserForm);
                return "auth/register";
            }
            else
            {
                User user = new User();
                user.setUsername(signUpUserForm.getUsername());
                user.setPassword(passwordEncoder.encode(signUpUserForm.getPassword()));

                usersRepository.save(user); // сохраняем пользователя

                try
                {
                    request.login(signUpUserForm.getUsername(), signUpUserForm.getPassword());  // выполняем принудительную авторизацию
                    return "redirect:/";
                }
                catch (ServletException e)  // если произошла ошибка
                {
                    e.printStackTrace();
                    return "redirect:/auth";
                }
            }
        }
    }
}
