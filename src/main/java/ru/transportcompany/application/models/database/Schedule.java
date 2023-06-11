package ru.transportcompany.application.models.database;

import lombok.Data;
import ru.transportcompany.application.models.database.Route;
import ru.transportcompany.application.models.database.Transport;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

@Entity
@Table(name = "schedule")
@Data
public class Schedule
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route")
    private Route route;

    @Column(name = "time_start")
    private LocalTime timeStart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transport")
    private Transport transport;

    @Column(name = "date")
    private Date date;

    public String get_dd_MMMM_yyyy_FormatDate()
    {
        SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd MMMM yyyy");
        return outputDateFormat.format(this.date);
    }

    public LocalTime getTimeEnd()
    {
        return timeStart.plusMinutes(route.getRouteTime().longValue());
    }

    public Date getDate()
    {
        date.setHours(timeStart.getHour());
        date.setMinutes(timeStart.getMinute());
        return date;
    }
}