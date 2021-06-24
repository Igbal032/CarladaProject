package az.code.carlada.daos.interfaces;

import az.code.carlada.enums.BodyType;
import az.code.carlada.enums.FuelType;
import az.code.carlada.models.City;
import az.code.carlada.models.Make;
import az.code.carlada.models.Model;
import az.code.carlada.models.Specification;

import java.util.List;
import java.util.Set;

public interface DictionaryDAO {
    List<Make> getMakes();

    List<Model> getModels(Long id);

    List<City> getCities();

    List<Specification> getSpecifications();

    Set<FuelType> getFuelTypes();

    Set<BodyType> getBodyTypes();

    Make findMakeById(Long id);

    Model findModelById(Long id);

    City findCityById(Long id);

    Specification findSpecificationById(Long id);

    List<Specification> findAllSpecificationById(Iterable<Long> id);
}
