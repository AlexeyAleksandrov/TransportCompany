package ru.transportcompany.application.controllers.advice;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.transportcompany.application.models.requests.CurrentUser;
import ru.transportcompany.application.models.users.User;
import ru.transportcompany.application.repositories.UsersRepository;

@ControllerAdvice
@AllArgsConstructor
public class UserControllerAdvice
{
    UsersRepository usersRepository;

    @ModelAttribute
    public void addAttributes(Model model, Authentication authentication)
    {
        boolean isAuth = false;
        if (authentication != null && authentication.isAuthenticated())
        {
            String userName = "неавторизованный пользователь";
            userName = authentication.getName();
            User user = usersRepository.findByUsername(userName).orElse(null);
            if (user != null)
            {
                isAuth = true;
            }
            model.addAttribute("user_name", userName);
            model.addAttribute("user", user);
        }
        model.addAttribute("is_auth", isAuth);
    }
}