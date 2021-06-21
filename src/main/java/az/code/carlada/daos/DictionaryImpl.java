package az.code.carlada.daos;

import az.code.carlada.enums.BodyType;
import az.code.carlada.enums.FuelType;
import az.code.carlada.models.City;
import az.code.carlada.models.Make;
import az.code.carlada.models.Model;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class DictionaryImpl implements DictionaryDAO{
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Make> getMakes() {
        return null;
    }

    @Override
    public List<Model> getModels(Long id) {
        return null;
    }

    @Override
    public List<City> getCities() {
        return null;
    }

    @Override
    public List<FuelType> getFuelTypes() {
        return null;
    }

    @Override
    public List<BodyType> getBodyTypes() {
        return null;
    }
}
