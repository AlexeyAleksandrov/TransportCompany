package ru.transportcompany.application.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter
{
    String[] allowMatchers = {"/", "/auth", "/register", "/index", "/upload", "/download**", "/css**"};

    // сервис для конвертации пользователей в UserDetails
    final SecurityUserDetailsService securityUserDetailsService;

    public SecurityConfiguration(SecurityUserDetailsService securityUserDetailsService)
    {
        this.securityUserDetailsService = securityUserDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http
                .csrf().disable()   // отключаем csrf
                .authorizeRequests()
//                .antMatchers("/**").permitAll()
                .antMatchers(allowMatchers).permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()    // настраиваем страницу авторизации
                .loginPage("/authlogin").permitAll()    // разрешаем все запросы к странице авторизации
                .defaultSuccessUrl("/") // по умолчанию перенаправляем на главную страницу
                .and()
                .rememberMe()   //  делаем возврат пользователя по сохранённой сессии
                .and()
                .logout()   // настраиваем выход из аккаунта
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "POST")) // чтобы выйти из аккаунта, надо отправить POST запрос
                .invalidateHttpSession(true)    // закрываем сессию
                .clearAuthentication(true)      // сбрасываем авторизацию
                .deleteCookies("JSESSIONID")    // удаляем куки
                .logoutSuccessUrl("/auth");     // перенаправляем на домашнюю страницу
    }

    @Bean
    protected PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder(12);
    }

    protected DaoAuthenticationProvider daoAuthenticationProvider()
    {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(securityUserDetailsService);
        return daoAuthenticationProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Override
    public void configure(WebSecurity web) throws Exception
    {
        web.ignoring().antMatchers("/css/**","/js/**","/fonts/**","/images/**");
    }
}
