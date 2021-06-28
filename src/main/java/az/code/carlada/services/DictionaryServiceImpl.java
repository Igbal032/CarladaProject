package az.code.carlada.services;

import az.code.carlada.components.ModelMapperComponent;
import az.code.carlada.daos.interfaces.DictionaryDAO;
import az.code.carlada.dtos.CityDTO;
import az.code.carlada.dtos.MakeDTO;
import az.code.carlada.dtos.ModelDTO;
import az.code.carlada.enums.BodyType;
import az.code.carlada.enums.FuelType;
import az.code.carlada.models.City;
import az.code.carlada.models.Make;
import az.code.carlada.services.interfaces.DictionaryService;
import org.springframework.stereotype.Service;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

@Service
public class DictionaryServiceImpl implements DictionaryService {

    DictionaryDAO dictionaryDAO;
    ModelMapperComponent mapperService;

    public DictionaryServiceImpl(DictionaryDAO dictionaryDAO, ModelMapperComponent mapperService) {
        this.dictionaryDAO = dictionaryDAO;
        this.mapperService = mapperService;
    }

    @Override
    public List<MakeDTO> getMakes() {
        return mapperService.mapList(dictionaryDAO.getData(Make.class), MakeDTO.class);
    }

    @Override
    public List<ModelDTO> getModels(Long id) {
        return mapperService.mapList(dictionaryDAO.getModelsByMake(id), ModelDTO.class);
    }

    @Override
    public List<CityDTO> getCities() {
        return mapperService.mapList(dictionaryDAO.getData(City.class), CityDTO.class);
    }

    @Override
    public Set<FuelType> getFuelTypes() {
        return mapperService.mapSet(EnumSet.allOf(FuelType.class), FuelType.class);
    }

    @Override
    public Set<BodyType> getBodyTypes() {
        return mapperService.mapSet(EnumSet.allOf(BodyType.class), BodyType.class);
    }
}
