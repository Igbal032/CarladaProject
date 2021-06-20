package az.code.carlada.services;

import az.code.carlada.dtos.CityDTO;
import az.code.carlada.dtos.MakeDTO;
import az.code.carlada.dtos.ModelDTO;
import az.code.carlada.enums.BodyType;
import az.code.carlada.enums.FuelType;

import java.util.List;
import java.util.Set;

public interface DictionaryService {
    List<MakeDTO> getMakes();
    List<ModelDTO> getModels(Long id);
    List<CityDTO> getCities();
    Set<FuelType> getFuelTypes();
    Set<BodyType> getBodyTypes();
}
