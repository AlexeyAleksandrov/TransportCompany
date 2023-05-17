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

//    private UsersRepository usersRepository;
//
//    @ModelAttribute
//    public void addAttributes(Model model, Authentication authentication) {
//        if (authentication != null && authentication.isAuthenticated()) {
//            String userName = authentication.getName();
//            User user = usersRepository.findByUsername(userName).orElse(null);
//
//            model.addAttribute("currentUser", new CurrentUser(authentication));
//            model.addAttribute("user", user);
//        }
//    }

//    @ModelAttribute("user_name")
//    public String getCurrentUser(Authentication authentication)     // добавление имени пользователя в каждый запрос
//    {
//        String currentUser = "неавторизованный пользователь";
//        if (authentication != null)
//        {
//            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//            currentUser = userDetails.getUsername();
//        }
//        return currentUser;
//    }

    @ModelAttribute
    public void addAttributes(Model model, Authentication authentication)
    {
        String userName = "неавторизованный пользователь";
        if (authentication != null && authentication.isAuthenticated())
        {
            userName = authentication.getName();
        }
        model.addAttribute("user", userName);
    }
}