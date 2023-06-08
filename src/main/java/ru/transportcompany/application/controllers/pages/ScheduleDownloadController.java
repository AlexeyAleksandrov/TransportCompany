package ru.transportcompany.application.controllers.pages;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import ru.transportcompany.application.controllers.database.TicketsController;
import ru.transportcompany.application.models.database.Schedule;
import ru.transportcompany.application.repositories.ScheduleRepository;
import ru.transportcompany.application.services.ScheduleService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    public void downloadSchedule(@RequestParam("date") String date, HttpServletRequest request, HttpServletResponse response, Model model)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        Date searchDate = new Date();
        try
        {
            Date searchDate = dateFormat.parse(date);
            System.out.println("download date: " + searchDate);
            List<Schedule> schedules = scheduleService.getSchedulesForNextWeekByDate(searchDate);
            scheduleService.createDocument(schedules);
//            return "redirect:/";

            File file = new File("C:\\Users\\Public\\document.docx");
            if (file.exists())
            {

                //get the mimetype
                String mimeType = URLConnection.guessContentTypeFromName(file.getName());
                if (mimeType == null)
                {
                    //unknown mimetype so set the mimetype to application/octet-stream
                    mimeType = "application/octet-stream";
                }

                response.setContentType(mimeType);

                /**
                 * In a regular HTTP response, the Content-Disposition response header is a
                 * header indicating if the content is expected to be displayed inline in the
                 * browser, that is, as a Web page or as part of a Web page, or as an
                 * attachment, that is downloaded and saved locally.
                 *
                 */

                /**
                 * Here we have mentioned it to show inline
                 */
                response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");

                //Here we have mentioned it to show as attachment
                //response.setHeader("Content-Disposition", String.format("attachment; filename=\"" + file.getName() + "\""));

                response.setContentLength((int) file.length());

                InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

                FileCopyUtils.copy(inputStream, response.getOutputStream());
            }
//            return "redirect:/";
        }
        catch (ParseException e)
        {
            e.printStackTrace();
            model.addAttribute("date_error", "Некорректная дата");
//            return "schedule/download";
        }
        catch (IOException e)
        {
            e.printStackTrace();
            model.addAttribute("file_error", "Не удалось сформировать файл!");
//            return "schedule/download";
        }
    }
}
