package az.code.carlada.services;

import az.code.carlada.components.ModelMapperComponent;
import az.code.carlada.daos.DictionaryDAO;
import az.code.carlada.dtos.CityDTO;
import az.code.carlada.dtos.MakeDTO;
import az.code.carlada.dtos.ModelDTO;
import az.code.carlada.enums.BodyType;
import az.code.carlada.enums.FuelType;
import org.springframework.stereotype.Service;

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
        return mapperService.mapList(dictionaryDAO.getMakes(), MakeDTO.class);
    }

    @Override
    public List<ModelDTO> getModels(Long id) {
        return mapperService.mapList(dictionaryDAO.getModels(id), ModelDTO.class);
    }

    @Override
    public List<CityDTO> getCities() {
        return mapperService.mapList(dictionaryDAO.getCities(), CityDTO.class);
    }

    @Override
    public Set<FuelType> getFuelTypes() {
        return mapperService.mapSet(dictionaryDAO.getFuelTypes(), FuelType.class);
    }

    @Override
    public Set<BodyType> getBodyTypes() {
        return mapperService.mapSet(dictionaryDAO.getBodyTypes(), BodyType.class);
    }
}
