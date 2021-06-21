package az.code.carlada.utils;


import az.code.carlada.dtos.*;
import az.code.carlada.enums.BodyType;
import az.code.carlada.enums.Color;
import az.code.carlada.enums.FuelType;
import az.code.carlada.enums.Status;
import az.code.carlada.models.Listing;
import az.code.carlada.models.Subscription;
import lombok.Builder;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static az.code.carlada.utils.BasicUtil.getEnumFromString;

@Builder
public class ModelMapperUtil {
    public ModelMapper modelMapper;


    public <E, T> List<T> mapList(Collection<? extends E> list, Class<T> type) {
        return list.stream().map(i -> modelMapper.map(i, type)).collect(Collectors.toList());
    }

    public <E, T> Set<T> mapSet(Collection<? extends E> list, Class<T> type) {
        return list.stream().map(i -> modelMapper.map(i, type)).collect(Collectors.toSet());
    }


    public ListingListDTO convertListingToListDto(Listing i) {
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


    public ListingGetDTO convertListingToListingGetDto(Listing i) {
        return ListingGetDTO.builder()
                .id(i.getId())
                .type(i.getType().name())
                .year(i.getCar().getYear())
                .autoPay(i.getAutoPay())
                .carSpecs(mapList(i.getCar().getCarDetail().getCarSpecifications(), CarSpecDTO.class))
                .bodyType(i.getCar().getCarDetail().getBodyType().name())
                .user(modelMapper.map(i.getAppUser(), AppUserDTO.class))
                .city(modelMapper.map(i.getCity(), CityDTO.class))
                .color(i.getCar().getCarDetail().getColor().toString())
                .cashOption(i.getCar().getCashOption())
                .creditOption(i.getCar().getLoanOption())
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

    public Listing convertCreationDtoToListing(ListingCreationDTO i) {
        return Listing.builder()
                .autoPay(i.getAuto_pay())
                .description(i.getDescription())
                .type(getEnumFromString(Status.class, i.getType()))
                .thumbnailUrl(i.getThumbnailUrl())
                .build();


    }

    public Subscription convertDTOToSubscription(SubscriptionDTO s) {
        return Subscription.builder()
                .name(s.getName())
                .subId(s.getSubId())
                .bodyType(getEnumFromString(BodyType.class, s.getBodyType()))
                .fuelType(getEnumFromString(FuelType.class, s.getFuelType()))
                .color(getEnumFromString(Color.class, s.getColor()))
                .barterOption(s.getBarterOption())
                .loanOption(s.getLoanOption())
                .leaseOption(s.getLeaseOption())
                .cashOption(s.getCashOption())
                .creationDate(LocalDateTime.now())
                .minPrice(s.getMinPrice())
                .maxPrice(s.getMaxPrice())
                .minYear(s.getMinYear())
                .maxYear(s.getMaxYear())
                .minMileage(s.getMinMileage())
                .maxMileage(s.getMaxMileage())
                .build();
    }

    public SubscriptionListDTO convertSubscriptionToListDTO(Subscription s) {

        return SubscriptionListDTO.builder()
                .name(s.getName())
                .subId(s.getSubId())
                .bodyType(s.getBodyType())
                .fuelType(s.getFuelType())
                .color(s.getColor())
                .barterOption(s.getBarterOption())
                .loanOption(s.getLoanOption())
                .leaseOption(s.getLeaseOption())
                .cashOption(s.getCashOption())
                .creationDate(LocalDateTime.now())
                .minPrice(s.getMinPrice())
                .maxPrice(s.getMaxPrice())
                .minYear(s.getMinYear())
                .maxYear(s.getMaxYear())
                .minMileage(s.getMinMileage())
                .maxMileage(s.getMaxMileage())
                .specs(mapList(s.getSpecs(), CarSpecDTO.class))
                .city(modelMapper.map(s.getCity(), CityDTO.class))
                .model(modelMapper.map(s.getModel(), ModelDTO.class))
                .make(modelMapper.map(s.getModel().getMake(), MakeDTO.class))
                .build();
    }
}
