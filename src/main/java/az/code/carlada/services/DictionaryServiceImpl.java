package az.code.carlada.services;

import az.code.carlada.daos.DictionaryDAO;
import az.code.carlada.dtos.CityDTO;
import az.code.carlada.dtos.MakeDTO;
import az.code.carlada.dtos.ModelDTO;
import az.code.carlada.enums.BodyType;
import az.code.carlada.enums.FuelType;
import az.code.carlada.utils.ModelMapperUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class DictionaryServiceImpl implements DictionaryService {

    DictionaryDAO dictionaryDAO;
    ModelMapperUtil mapperUtil;

    public DictionaryServiceImpl(DictionaryDAO dictionaryDAO, ModelMapper modelMapper) {
        this.dictionaryDAO = dictionaryDAO;
        this.mapperUtil = new ModelMapperUtil(modelMapper);
    }

    @Override
    public List<MakeDTO> getMakes() {
        return mapperUtil.mapList(dictionaryDAO.getMakes(), MakeDTO.class);
    }

    @Override
    public List<ModelDTO> getModels(Long id) {
        return mapperUtil.mapList(dictionaryDAO.getModels(id), ModelDTO.class);
    }

    @Override
    public List<CityDTO> getCities() {
        return mapperUtil.mapList(dictionaryDAO.getCities(), CityDTO.class);
    }

    @Override
    public Set<FuelType> getFuelTypes() {
        return mapperUtil.mapSet(dictionaryDAO.getFuelTypes(), FuelType.class);
    }

    @Override
    public Set<BodyType> getBodyTypes() {
        return mapperUtil.mapSet(dictionaryDAO.getBodyTypes(), BodyType.class);
    }
}
