package ru.transportcompany.application.models.requests;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import ru.transportcompany.application.models.users.User;

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
@AllArgsConstructor
public class CurrentUser {

    private final Authentication authentication;

    public String getUsername() {
        return authentication.getName();
    }

    public User getUser() {
        Object principal = authentication.getPrincipal();

        if (principal instanceof User) {
            return (User) principal;
        }

        return null;
    }
}
