package az.code.carlada.utils;

import az.code.carlada.dtos.*;
import az.code.carlada.enums.BodyType;
import az.code.carlada.enums.Color;
import az.code.carlada.enums.FuelType;
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
