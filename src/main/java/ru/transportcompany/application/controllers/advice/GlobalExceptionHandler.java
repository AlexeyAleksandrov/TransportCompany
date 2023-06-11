package ru.transportcompany.application.controllers.advice;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler
{
    @ExceptionHandler(org.postgresql.util.PSQLException.class)
    public String handleException(org.postgresql.util.PSQLException ex, Model model)
    {
        String errorMessage = Objects.requireNonNull(ex.getServerErrorMessage()).getMessage();
        System.out.println("ServerErrorMessage: " + errorMessage);

        model.addAttribute("error_message", "Ошибка: " + errorMessage);
        return "error";
    }

    // другие методы для обработки других исключений
}
