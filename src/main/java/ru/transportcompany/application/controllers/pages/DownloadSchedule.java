package ru.transportcompany.application.controllers.pages;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/schedule")
public class DownloadSchedule
{
    @GetMapping("/download")
    public String getDownloadSchedulePage()
    {
        return "redirect:/";
    }
}
