package ru.transportcompany.application.models.enums;

public enum ScheduleDocumentTableColumns
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

    ScheduleDocumentTableColumns(String columnName)
    {
        this.columnName = columnName;
    }
}
