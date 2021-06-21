package az.code.carlada.utils;



import az.code.carlada.dtos.*;
import az.code.carlada.enums.Status;
import az.code.carlada.models.Listing;
import org.modelmapper.ModelMapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


public class ModelMapperUtil {

    ModelMapper modelMapper;

    public ModelMapperUtil(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public <E, T> List<T> mapList(Collection<? extends E> list, Class<T> type) {
        return list.stream().map(i -> modelMapper.map(i, type)).collect(Collectors.toList());
    }

    public ListingListDTO convertListingToListDto(Listing i){
        return ListingListDTO.builder()
                .id(i.getId())
                .makeName(i.getCar().getModel().getMake().getMakeName())
                .modelName(i.getCar().getModel().getName())
                .thumbnailUrl(i.getThumbnailUrl())
                .cityName(i.getCity().getName())
                .mileage(i.getCar().getMileage())
                .price(i.getCar().getPrice())
                .updatedAt(i.getUpdatedAt())
                .year(i.getCar().getYear())
                .build();
    }

    public ListingGetDTO convertListingToListingGetDto(Listing i){
        return ListingGetDTO.builder()
                .id(i.getId())
                .type(i.getType().name())
                .year(i.getCar().getYear())
                .autoPay(i.getAutoPay())
                .carSpecs(mapList(i.getCar().getCarDetail().getCarSpecifications(), CarSpecDTO.class))
                .bodyType(i.getCar().getCarDetail().getBodyType().name())
                .user(modelMapper.map(i.getAppUser(), UserDTO.class))
                .city(modelMapper.map(i.getCity(), CityDTO.class))
                .color(i.getCar().getCarDetail().getColor().toString())
                .cashOption(i.getCar().getCashOption())
                .creditOption(i.getCar().getCreditOption())
                .barterOption(i.getCar().getBarterOption())
                .fuelType(i.getCar().getCarDetail().getFuelType().toString())
                .description(i.getDescription())
                .gearBox(i.getCar().getCarDetail().getGearBox().toString())
                .model(modelMapper.map(i.getCar().getModel(), ModelDTO.class))
                .make(modelMapper.map(i.getCar().getModel().getMake(), MakeDTO.class))
                .isActive(i.getIsActive())
                .leaseOption(i.getCar().getLeaseOption())
                .mileage(i.getCar().getMileage())
                .price(i.getCar().getPrice())
                .thumbnailUrl(i.getThumbnailUrl())
                .updatedAt(i.getUpdatedAt())
                .build();
    }

//    public ListingCreationDTO convertListingToListingCreationDto(Listing i){
//        return ListingCreationDTO.builder()
//                .makeId(i.getCar().getModel().getMake().getId())
//                .modelId(i.getCar().getModel().getId())
//                .year(i.getCar().getYear())
//                .price(i.getCar().getPrice())
//                .mileage(i.getCar().getMileage())
//                .fuelType(i.getCar().getCarDetail().getFuelType().toString())
//                .bodyType(i.getCar().getCarDetail().getBodyType().toString())
//                .color(i.getCar().getCarDetail().getColor().toString())
//                .cityId(i.getCity().getId())
//                .gearBox(i.getCar().getCarDetail().getGearBox().toString())
//                .auto_pay(i.getAutoPay())
//                .creditOption(i.getCar().getCreditOption())
//                .barterOption(i.getCar().getBarterOption())
//                .leaseOption(i.getCar().getLeaseOption())
//                .cashOption(i.getCar().getCashOption())
//                .description(i.getDescription())
//                .type(i.getType().toString())
//                .thumbnailUrl(i.getThumbnailUrl())
//                .build();
//    }

    public Listing convertCreationDtoToListing(ListingCreationDTO i){
        return Listing.builder()
                .autoPay(i.getAuto_pay())
                .description(i.getDescription())
                .type(BasicUtil.getEnumFromString(Status.class, i.getType()))
                .thumbnailUrl(i.getThumbnailUrl())
                .build();


    }
}