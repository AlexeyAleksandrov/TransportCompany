package ru.transportcompany.application.services;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.stereotype.Service;
import ru.transportcompany.application.models.database.Schedule;
import ru.transportcompany.application.models.enums.ScheduleDocumentTableColumns;
import ru.transportcompany.application.repositories.ScheduleRepository;
import ru.transportcompany.application.repositories.TicketsRepository;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ScheduleService
{
    private ScheduleRepository scheduleRepository;
    private TicketsRepository ticketsRepository;

    public void createDocument(List<Schedule> schedules) throws IOException
    {
        int rows = schedules.size() + 1;
        int cols = ScheduleDocumentTableColumns.values().length;

        // Create document
        XWPFDocument document = new XWPFDocument();

        // Create table
        XWPFTable table = document.createTable(rows, cols);

        // Set table style
        table.getCTTbl().addNewTblPr().addNewTblW().setW(BigInteger.valueOf(5000));

        // заголовки
        for (int i = 0; i < cols; i++)
        {
            table.getRow(0).getCell(i).setText(ScheduleDocumentTableColumns.values()[i].columnName);
        }

        // формируем дату
        SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd MMMM yyyy");

        // маршруты
        for (int i = 0; i < schedules.size(); i++)
        {
            Schedule schedule = schedules.get(i);

            XWPFTableRow row = table.getRow(i+1);   // 0 строка отдана под заголовки
            row.getCell(ScheduleDocumentTableColumns.DATE.ordinal()).setText(outputDateFormat.format(schedule.getDate()));    // дата
            row.getCell(ScheduleDocumentTableColumns.ROUTE_NUMBER.ordinal()).setText(schedule.getRoute().getRouteNumber());      // номер маршрута
            row.getCell(ScheduleDocumentTableColumns.POINT_FROM.ordinal()).setText(schedule.getRoute().getRouteStartPoint().getPointName());     // точка отправления
            row.getCell(ScheduleDocumentTableColumns.POINT_TO.ordinal()).setText(schedule.getRoute().getRouteFinishPoint().getPointName());      // точка прибытия
            row.getCell(ScheduleDocumentTableColumns.TIME_START.ordinal()).setText(schedule.getTimeStart().toString());      // время отправления
            row.getCell(ScheduleDocumentTableColumns.TIME_END.ordinal()).setText(schedule.getTimeEnd().toString());    // время прибытия
            row.getCell(ScheduleDocumentTableColumns.COST_FOR_ADULT.ordinal()).setText(schedule.getRoute().getCostForAdult().toString());    // стоимость для взрослого
            row.getCell(ScheduleDocumentTableColumns.COST_FOR_CHILD.ordinal()).setText(schedule.getRoute().getCostForChild().toString());    // стоимость для ребёнка
        }

        // Save document
            FileOutputStream outputStream = new FileOutputStream("C:\\Users\\Public\\document.docx");
            document.write(outputStream);
            outputStream.close();
    }

    public List<Schedule> getSchedulesForNextWeekByDate(Date searchDate)
    {
        return scheduleRepository.findAll().stream()
                .filter((schedule -> {
                    long diffInMillies = schedule.getDate().getTime() - searchDate.getTime();
                    long diffInDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
                    return (diffInDays <= 7 && (searchDate.compareTo(schedule.getDate()) <= 0));

//                    LocalDateTime searchDateTime = LocalDateTime.of(searchDate.getYear(), searchDate.getMonth(), searchDate.getDate(), 0, 0);
//                    LocalDateTime scheduleDateTime = LocalDateTime.of(LocalDate.of(schedule.getDate().getYear(), schedule.getDate().getMonth(), schedule.getDate().getDate()), schedule.getTimeStart());
//                    return scheduleDateTime.isAfter(searchDateTime) && scheduleDateTime.isBefore(searchDateTime.plusDays(7));
                }))
                .sorted(new Comparator<Schedule>() {
                    @Override
                    public int compare(Schedule o1, Schedule o2)
                    {
                        return o1.getDate().compareTo(o2.getDate());
                    }
                })
                .collect(Collectors.toList());
    }

    public int getEmptySeatsCount(@NotNull Schedule schedule)
    {
        return ticketsRepository.countBySchedule(schedule);
    }

    /**
     * @return список всех актуальных маршрутов, которые доступны сейчас
     */
    public List<Schedule> getActualSchedules()
    {
        Date nowDate = new Date();
        return scheduleRepository.findAll().stream()
                .filter((schedule ->
                {
//                    LocalDateTime scheduleDateTime = LocalDateTime.of(LocalDate.of(1900 + schedule.getDate().getYear(), schedule.getDate().getMonth(), schedule.getDate().getDate()), schedule.getTimeStart());
//                    LocalDateTime nowDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.now());
//                    System.out.println("scheduleDateTime: " + scheduleDateTime);
//                    System.out.println("nowDateTime: " + nowDateTime);
//                    System.out.println("scheduleDateTime: " + scheduleDateTime.isAfter(nowDateTime) + " nowDateTime");
//                    return nowDateTime.isBefore(scheduleDateTime);

//                    System.out.println(schedule.getDate() + " " + schedule.getDate().compareTo(nowDate) + " " +  nowDate);
                    return schedule.getDate().compareTo(nowDate) >= 0;
                }))
                .collect(Collectors.toList());      // выбираем все маршруты, которые доступны
    }


}
