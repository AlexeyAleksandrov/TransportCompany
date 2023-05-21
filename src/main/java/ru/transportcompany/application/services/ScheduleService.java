package ru.transportcompany.application.services;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.springframework.stereotype.Service;
import ru.transportcompany.application.models.database.Schedule;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

enum Columns
{
    DAY_OF_WEEK,
    ROUTE_NUMBER,
    POINT_FROM,
    POINT_TO,
    TIME_START,
    TIME_END,
    COST_FOR_ADULT,
    COST_FOR_CHILD
}

@Service
public class ScheduleService
{
    public void createDocument(List<Schedule> schedules)
    {
        int rows = schedules.size();
        int cols = Columns.values().length;

        // Create document
        XWPFDocument document = new XWPFDocument();

        // Create table
        XWPFTable table = document.createTable(rows, cols);

        // Set table style
        table.getCTTbl().addNewTblPr().addNewTblW().setW(BigInteger.valueOf(5000));

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
        try
        {
            FileOutputStream outputStream = new FileOutputStream("C:\\Users\\Public\\document.docx");
            document.write(outputStream);
            outputStream.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
