package ru.transportcompany.application.database.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.transportcompany.application.database.DataBase;
import ru.transportcompany.application.database.models.Driver;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class DriversDAO
{
    @Autowired
    DataBase dataBase;

    /** Функция получения данных об водителе
     * @param id id водителя
     * @return данные об водителе
     */
    public Driver getDriverById(int id)
    {
        Driver driver = new Driver();
        try
        {
            ResultSet resultSet;
            try (PreparedStatement preparedStatement = dataBase.getConnection().prepareStatement(
                    "SELECT * FROM drivers WHERE id = ?"))
            {
                preparedStatement.setInt(1, id);
                resultSet = preparedStatement.executeQuery();
            }

            if(resultSet.next())
            {
                driver.setId(resultSet.getLong("id"));
                driver.setFirstName(resultSet.getString("first_name"));
                driver.setLastName(resultSet.getString("last_name"));
                driver.setPatronymic(resultSet.getString("patronymic"));
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
            preparedStatement.setString(3, driver.getLastName());

            return preparedStatement.executeUpdate();
        }
        catch (SQLException throwable)
        {
            throwable.printStackTrace();
        }
        return 0;
    }
}
