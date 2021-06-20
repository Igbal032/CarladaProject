package az.code.carlada.utils;

import az.code.carlada.dtos.ListingListDTO;
import az.code.carlada.models.Listing;
import org.modelmapper.ModelMapper;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class ModelMapperUtil {
    public ModelMapper modelMapper;

    public ModelMapperUtil(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public <E, T> List<T> mapList(Collection<? extends E> list, Class<T> type) {
        return list.stream().map(i -> modelMapper.map(i, type)).collect(Collectors.toList());
    }
    public <E, T> Set<T> mapSet(Collection<? extends E> list, Class<T> type) {
        return list.stream().map(i -> modelMapper.map(i, type)).collect(Collectors.toSet());
    }

    public ListingListDTO convertListingToListDto(Listing i){
        return ListingListDTO.builder()
                .id(i.getId())
                .makeName(i.getCar().getModel().getMake().getMakeName())
                .modelName(i.getCar().getModel().getModelName())
                .thumbnailUrl(i.getThumbnailUrl())
                .cityName(i.getCity().getCityName())
                .mileage(i.getCar().getMileage())
                .price(i.getCar().getPrice())
                .updatedAt(i.getUpdatedAt())
                .year(i.getCar().getYear())
                .build();
    }
}
