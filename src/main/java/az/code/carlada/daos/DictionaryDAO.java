package az.code.carlada.daos;

import az.code.carlada.enums.BodyType;
import az.code.carlada.enums.FuelType;
import az.code.carlada.models.City;
import az.code.carlada.models.Make;
import az.code.carlada.models.Model;

import java.util.List;
import java.util.Set;

public interface DictionaryDAO {
    List<Make> getMakes();
    List<Model> getModels(Long id);
    List<City> getCities();
    Set<FuelType> getFuelTypes();
    Set<BodyType> getBodyTypes();
}
