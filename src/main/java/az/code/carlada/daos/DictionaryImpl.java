package az.code.carlada.daos;

import az.code.carlada.enums.BodyType;
import az.code.carlada.enums.FuelType;
import az.code.carlada.models.City;
import az.code.carlada.models.Make;
import az.code.carlada.models.Model;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

@Component
public class DictionaryImpl implements DictionaryDAO {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Make> getMakes() {
        return entityManager.createQuery("select m from Make m", Make.class).getResultList();
    }

    @Override
    public List<Model> getModels(Long id) {
        return entityManager.createQuery("select m from Model m where m.make.id=:makeId", Model.class)
                .setParameter("makeId", id).getResultList();
    }

    @Override
    public List<City> getCities() {
        return entityManager.createQuery("select c from City c", City.class).getResultList();
    }

    @Override
    public Set<FuelType> getFuelTypes() {
        return EnumSet.allOf(FuelType.class);
    }

    @Override
    public Set<BodyType> getBodyTypes() {
        return EnumSet.allOf(BodyType.class);
    }
}
