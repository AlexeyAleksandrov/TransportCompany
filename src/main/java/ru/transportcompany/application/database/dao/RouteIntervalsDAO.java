package ru.transportcompany.application.database.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.transportcompany.application.database.DataBase;
import ru.transportcompany.application.database.models.RouteInterval;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
public class RouteIntervalsDAO
{
    @Autowired
    DataBase dataBase;

    public int addRouteInterval(RouteInterval routeInterval)
    {
        try(PreparedStatement preparedStatement = dataBase.getConnection().prepareStatement(
                "INSERT INTO route_intervals (interval_name) VALUES (?)"))
        {
            preparedStatement.setString(1, routeInterval.getIntervalName());

            return preparedStatement.executeUpdate();
        }
        catch (SQLException throwable)
        {
            throwable.printStackTrace();
        }
        return 0;
    }
}
