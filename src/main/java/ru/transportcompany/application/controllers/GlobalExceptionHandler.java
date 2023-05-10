package ru.transportcompany.application.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler
{
    @ExceptionHandler(org.postgresql.util.PSQLException.class)
    public String handleException(org.postgresql.util.PSQLException ex, Model model)
    {
        model.addAttribute("message", ex.getLocalizedMessage());
        return "error";
    }

    // другие методы для обработки других исключений
}
