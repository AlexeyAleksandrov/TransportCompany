package ru.transportcompany.application.models.forms.drivers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.transportcompany.application.models.database.Driver;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvalidFlight
{
    private Driver driver;
    private List<String> reasons;
}
