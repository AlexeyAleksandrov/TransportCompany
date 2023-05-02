package ru.transportcompany.application.database.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.transportcompany.application.database.DataBase;
import ru.transportcompany.application.database.models.Driver;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Component
public class DriversDAO
{
    @Autowired
    DataBase dataBase;

    /** Функция получения данных об водителе
     * @param id id водителя
     * @return данные об водителе
     */
    public Driver getDriverById(long id)
    {
        Driver driver = new Driver();
        try
        {
            ResultSet resultSet;
            try (PreparedStatement preparedStatement = dataBase.getConnection().prepareStatement(
                    "SELECT * FROM drivers WHERE id = ?"))
            {
                preparedStatement.setLong(1, id);
                resultSet = preparedStatement.executeQuery();

                if(resultSet.next())
                {
                    driver.setId(resultSet.getLong("id"));
                    driver.setFirstName(resultSet.getString("first_name"));
                    driver.setLastName(resultSet.getString("last_name"));
                    driver.setPatronymic(resultSet.getString("patronymic"));
                }
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return driver;
    }

    /** Добавление водителя в базу
     * @param driver информация об водителе
     * @return кол-во добавленных строк
     */
    public int addDriver(Driver driver)
    {
        try(PreparedStatement preparedStatement = dataBase.getConnection().prepareStatement(
                "INSERT INTO drivers (first_name, last_name, patronymic) VALUES (?, ?, ?)"))
        {
            preparedStatement.setString(1, driver.getFirstName());
            preparedStatement.setString(2, driver.getLastName());
            preparedStatement.setString(3, driver.getPatronymic());

            return preparedStatement.executeUpdate();
        }
        catch (SQLException throwable)
        {
            throwable.printStackTrace();
        }
        return 0;
    }

    public List<Driver> getAll()
    {
        List<Driver> drivers = new ArrayList<>();
        try
        {
            ResultSet resultSet;
            try (PreparedStatement preparedStatement = dataBase.getConnection().prepareStatement(
                    "SELECT * FROM drivers WHERE id > 0"))
            {
                resultSet = preparedStatement.executeQuery();

                while (resultSet.next())
                {
                    Driver driver = new Driver();

                    driver.setId(resultSet.getLong("id"));
                    driver.setFirstName(resultSet.getString("first_name"));
                    driver.setLastName(resultSet.getString("last_name"));
                    driver.setPatronymic(resultSet.getString("patronymic"));

                    drivers.add(driver);
                }
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return drivers;
    }
}
