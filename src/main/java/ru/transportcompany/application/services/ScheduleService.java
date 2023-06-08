package ru.transportcompany.application.services;

import lombok.AllArgsConstructor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.stereotype.Service;
import ru.transportcompany.application.models.database.Schedule;
import ru.transportcompany.application.models.enums.ScheduleDocumentTableColumns;
import ru.transportcompany.application.repositories.ScheduleRepository;

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
            row.getCell(ScheduleDocumentTableColumns.TIME_END.ordinal()).setText(schedule.getTimeStart().plusMinutes(schedule.getRoute().getRouteTime().longValue()).toString());    // время прибытия
            row.getCell(ScheduleDocumentTableColumns.COST_FOR_ADULT.ordinal()).setText(schedule.getRoute().getCostForAdult().toString());    // стоимость для взрослого
            row.getCell(ScheduleDocumentTableColumns.COST_FOR_CHILD.ordinal()).setText(schedule.getRoute().getCostForChild().toString());    // стоимость для ребёнка
        }

//        // Add content to cells
//        XWPFTableCell cell = table.getRow(0).getCell(0);
//        cell.setText("Header 1");
//        cell = table.getRow(0).getCell(1);
//        cell.setText("Header 2");
//        cell = table.getRow(0).getCell(2);
//        cell.setText("Header 3");
//        cell = table.getRow(1).getCell(0);
//        cell.setText("Row 1, Column 1");
//        cell = table.getRow(1).getCell(1);
//        cell.setText("Row 1, Column 2");
//        cell = table.getRow(1).getCell(2);
//        cell.setText("Row 1, Column 3");

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
}
