package az.code.carlada.daos;

import az.code.carlada.daos.interfaces.DictionaryDAO;
import az.code.carlada.enums.BodyType;
import az.code.carlada.enums.FuelType;
import az.code.carlada.exceptions.DataNotFound;
import az.code.carlada.models.City;
import az.code.carlada.models.Make;
import az.code.carlada.models.Model;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

import az.code.carlada.models.Specification;
import az.code.carlada.repositories.CityRepo;
import az.code.carlada.repositories.MakeRepo;
import az.code.carlada.repositories.ModelRepo;
import az.code.carlada.repositories.SpecificationRepo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DictionaryDAOImpl implements DictionaryDAO {
    MakeRepo makeRepo;
    ModelRepo modelRepo;
    CityRepo cityRepo;
    SpecificationRepo specRepo;

    public DictionaryDAOImpl(MakeRepo makeRepo, ModelRepo modelRepo, CityRepo cityRepo, SpecificationRepo specRepo) {
        this.makeRepo = makeRepo;
        this.modelRepo = modelRepo;
        this.cityRepo = cityRepo;
        this.specRepo = specRepo;
    }

    @Override
    public List<Make> getMakes() {
        return makeRepo.findAll();
    }

    @Override
    public List<Model> getModels(Long id) {
        return modelRepo.findByMakeId(id);
    }

    @Override
    public List<City> getCities() {
        return cityRepo.findAll();
    }

    @Override
    public List<Specification> getSpecifications() {
        return specRepo.findAll();
    }


    @Override
    public Set<FuelType> getFuelTypes() {
        return EnumSet.allOf(FuelType.class);
    }

    @Override
    public Set<BodyType> getBodyTypes() {
        return EnumSet.allOf(BodyType.class);
    }

    @Override
    public Make findMakeById(Long id) {
        if (id == null) return null;
        Optional<Make> make = makeRepo.findById(id);
        if (make.isEmpty()) throw new DataNotFound("Make couldn't found");
        return make.get();
    }

    @Override
    public Model findModelById(Long id) {
        if (id == null) return null;
        Optional<Model> model = modelRepo.findById(id);
        if (model.isEmpty()) throw new DataNotFound("Model couldn't found");
        return model.get();
    }

    @Override
    public City findCityById(Long id) {
        if (id == null) return null;
        Optional<City> city = cityRepo.findById(id);
        if (city.isEmpty()) throw new DataNotFound("City couldn't found");
        return city.get();
    }

    @Override
    public Specification findSpecificationById(Long id) {
        if (id == null) return null;
        Optional<Specification> spec = specRepo.findById(id);
        if (spec.isEmpty()) throw new DataNotFound("Specification couldn't found");
        return spec.get();
    }

    @Override
    public List<Specification> findAllSpecificationById(Iterable<Long> id) {
        if (id == null) return null;
        return specRepo.findAllById(id);
    }
}
