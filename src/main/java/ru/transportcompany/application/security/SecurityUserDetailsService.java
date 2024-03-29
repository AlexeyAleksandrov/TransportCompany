package ru.transportcompany.application.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.transportcompany.application.models.users.User;
import ru.transportcompany.application.repositories.UsersRepository;

@Service
public class SecurityUserDetailsService implements UserDetailsService
{
    final UsersRepository usersRepository;

//    List<User> users = new ArrayList<>(2);

    public SecurityUserDetailsService(UsersRepository usersRepository)     // временная реализация
    {
//        User user = new User();
//        user.setUsername("test@mail.ru");
//        user.setPassword("$2a$12$Ywt7QmgBirp23A3u4Bz/oeKuD0Lbu3G1BOYa2jVUmzj5ahgiNKxpC");   // 123456
//        user.setStatus(Status.ACTIVE);
//        users.add(user);
        this.usersRepository = usersRepository;
    }

    // переопределяем метод loadUserByUsername, который даст Spring Security информацию о пользователе
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
//        return SecurityUser.fromUser(users.get(0));
        return SecurityUser.fromUser(usersRepository.findByUsername(username).orElse(new User()));
    }
}
