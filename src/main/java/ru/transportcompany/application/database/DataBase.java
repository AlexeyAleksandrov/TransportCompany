package ru.transportcompany.application.database;

import org.springframework.stereotype.Component;
import ru.transportcompany.application.database.models.Driver;

import java.sql.*;
import java.util.Properties;

@Component
public class DataBase
{
    private final String URL = "jdbc:postgresql://localhost:5432/transport_company";
    private final String USERNAME = "postgres";
    private final String PASSWORD = "1111";

    private Connection connection;

    public DataBase()
    {
        try
        {
            Properties props = new Properties();
            props.setProperty("user", USERNAME);
            props.setProperty("password", PASSWORD);

            connection = DriverManager.getConnection(URL, props);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public Connection getConnection()
    {
        return connection;
    }
}
