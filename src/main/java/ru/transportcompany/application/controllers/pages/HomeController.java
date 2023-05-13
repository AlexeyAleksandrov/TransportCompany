package ru.transportcompany.application.controllers.pages;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.transportcompany.application.models.users.User;

@Controller
public class HomeController
{
    @GetMapping(value = "/")
    public String getHomePage()
    {
        return "home";
    }

    @ModelAttribute("user_name")
    public String getCurrentUser(Authentication authentication)     // добавление имени пользователя в каждый запрос
    {
        String currentUser = "неавторизованный пользователь";
        if (authentication != null)
        {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            currentUser = userDetails.getUsername();
        }
        return currentUser;
    }
}
