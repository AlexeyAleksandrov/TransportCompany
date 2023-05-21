package ru.transportcompany.application.services;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.stereotype.Service;
import ru.transportcompany.application.models.database.Schedule;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.List;

enum Columns
{
    DATE("Дата отправления"),
    ROUTE_NUMBER("Номер маршрута"),
    POINT_FROM("Точка отправления"),
    POINT_TO("Точка прибытия"),
    TIME_START("Время отправления"),
    TIME_END("Время прибытия"),
    COST_FOR_ADULT("Стоимость билета"),
    COST_FOR_CHILD("Стоимость детского билета");

    public String columnName;

    Columns(String columnName)
    {
        this.columnName = columnName;
    }
}

@Service
public class ScheduleService
{
    public void createDocument(List<Schedule> schedules) throws IOException
    {
        int rows = schedules.size() + 1;
        int cols = Columns.values().length;

        // Create document
        XWPFDocument document = new XWPFDocument();

        // Create table
        XWPFTable table = document.createTable(rows, cols);

        // Set table style
        table.getCTTbl().addNewTblPr().addNewTblW().setW(BigInteger.valueOf(5000));

        // заголовки
        for (int i = 0; i < cols; i++)
        {
            table.getRow(0).getCell(i).setText(Columns.values()[i].columnName);
        }

        // формируем дату
        SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd MMMM yyyy");

        // маршруты
        for (int i = 0; i < schedules.size(); i++)
        {
            Schedule schedule = schedules.get(i);

            XWPFTableRow row = table.getRow(i+1);   // 0 строка отдана под заголовки
            row.getCell(Columns.DATE.ordinal()).setText(outputDateFormat.format(schedule.getDate()));    // дата
            row.getCell(Columns.ROUTE_NUMBER.ordinal()).setText(schedule.getRoute().getRouteNumber());      // номер маршрута
            row.getCell(Columns.POINT_FROM.ordinal()).setText(schedule.getRoute().getRouteStartPoint().getPointName());     // точка отправления
            row.getCell(Columns.POINT_TO.ordinal()).setText(schedule.getRoute().getRouteFinishPoint().getPointName());      // точка прибытия
            row.getCell(Columns.TIME_START.ordinal()).setText(schedule.getTimeStart().toString());      // время отправления
            row.getCell(Columns.TIME_END.ordinal()).setText(schedule.getTimeStart().plusMinutes(schedule.getRoute().getRouteTime().longValue()).toString());    // время прибытия
            row.getCell(Columns.COST_FOR_ADULT.ordinal()).setText(schedule.getRoute().getCostForAdult().toString());    // стоимость для взрослого
            row.getCell(Columns.COST_FOR_CHILD.ordinal()).setText(schedule.getRoute().getCostForChild().toString());    // стоимость для ребёнка
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
}
