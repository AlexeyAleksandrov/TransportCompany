package ru.transportcompany.application.controllers.pages;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.transportcompany.application.models.database.Schedule;
import ru.transportcompany.application.repositories.ScheduleRepository;
import ru.transportcompany.application.services.ScheduleService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/schedule")
@AllArgsConstructor
public class ScheduleDownloadController
{
    private ScheduleService scheduleService;
    private ScheduleRepository scheduleRepository;

    @GetMapping("/download")
    public String getDownloadSchedulePage()
    {
        return "schedule/download";
    }

    @PostMapping("/download")
    public String downloadSchedule(@RequestParam("date") String date, Model model)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        Date searchDate = new Date();
        try
        {
            Date searchDate = dateFormat.parse(date);
            List<Schedule> schedules = scheduleRepository.findAll().stream()
                    .filter((schedule -> {
                        long diffInMillies = schedule.getDate().getTime() - searchDate.getTime();
                        long diffInDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
                        return (diffInDays <= 7);
                    }))
                    .sorted(new Comparator<Schedule>() {
                        @Override
                        public int compare(Schedule o1, Schedule o2)
                        {
                            return o1.getDate().compareTo(o2.getDate());
                        }
                    })
                    .collect(Collectors.toList());
            scheduleService.createDocument(schedules);
            return "redirect:/";
        }
        catch (ParseException e)
        {
            e.printStackTrace();
            model.addAttribute("date_error", "Некорректная дата");
            return "schedule/download";
        }
    }
}
