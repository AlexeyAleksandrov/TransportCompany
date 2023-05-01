package ru.transportcompany.application.database.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.transportcompany.application.database.DataBase;
import ru.transportcompany.application.database.models.TransportType;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
public class TransportTypesDAO
{
    @Autowired
    private DataBase dataBase;

    public int addTransportType(TransportType transportType)
    {
        try(PreparedStatement preparedStatement = dataBase.getConnection().prepareStatement(
                "INSERT INTO transport_types (name) VALUES (?)"))
        {
            preparedStatement.setString(1, transportType.getName());

            return preparedStatement.executeUpdate();
        }
        catch (SQLException throwable)
        {
            throwable.printStackTrace();
        }
        return 0;
    }
}
