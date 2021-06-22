package az.code.carlada.components;


import az.code.carlada.daos.DictionaryDAO;
import az.code.carlada.dtos.*;
import az.code.carlada.enums.BodyType;
import az.code.carlada.enums.Color;
import az.code.carlada.enums.FuelType;
import az.code.carlada.enums.Status;
import az.code.carlada.exceptions.DataNotFound;
import az.code.carlada.models.Listing;
import az.code.carlada.models.Subscription;
import lombok.Builder;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static az.code.carlada.utils.BasicUtil.getEnumFromString;

@Component
public class ModelMapperComponent {
    public ModelMapper modelMapper;
    public DictionaryDAO dictionaryDAO;

    public ModelMapperComponent(ModelMapper modelMapper, DictionaryDAO dictionaryDAO) {
        this.modelMapper = modelMapper;
        this.dictionaryDAO = dictionaryDAO;
    }

    public <E, T> List<T> mapList(Collection<? extends E> list, Class<T> type) {
        if (list == null) return null;
        return list.stream().map(i -> modelMapper.map(i, type)).collect(Collectors.toList());
    }

    public <E, T> T map(E item, Class<T> type) {
        if (item == null) return null;
        return modelMapper.map(item, type);
    }

    public <E, T> Set<T> mapSet(Collection<? extends E> list, Class<T> type) {
        if (list == null) return null;
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
                .autoPay(i.isAutoPay())
                .carSpecs(mapList(i.getCar().getCarDetail().getCarSpecifications(), CarSpecDTO.class))
                .bodyType(i.getCar().getCarDetail().getBodyType().name())
                .user(map(i.getAppUser(), AppUserDTO.class))
                .city(map(i.getCity(), CityDTO.class))
                .color(i.getCar().getCarDetail().getColor().toString())
                .cashOption(i.getCar().getCashOption())
                .creditOption(i.getCar().getLoanOption())
                .barterOption(i.getCar().getBarterOption())
                .fuelType(i.getCar().getCarDetail().getFuelType().toString())
                .description(i.getDescription())
                .gearBox(i.getCar().getCarDetail().getGearBox().toString())
                .model(map(i.getCar().getModel(), ModelDTO.class))
                .make(map(i.getCar().getModel().getMake(), MakeDTO.class))
                .isActive(i.isActive())
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
                .make(dictionaryDAO.findMakeById(s.getMakeId()))
                .model(dictionaryDAO.findModelById(s.getModelId()))
                .city(dictionaryDAO.findCityById(s.getCityId()))
                .specs(dictionaryDAO.findAllSpecificationById(s.getSpecs()))
                .bodyType(getEnumFromString(BodyType.class, s.getBodyType()))
                .fuelType(getEnumFromString(FuelType.class, s.getFuelType()))
                .color(getEnumFromString(Color.class, s.getColor()))
                .barterOption(s.getBarterOption())
                .loanOption(s.getLoanOption())
                .leaseOption(s.getLeaseOption())
                .cashOption(s.getCashOption())
                .minPrice(s.getMinPrice())
                .maxPrice(s.getMaxPrice())
                .minYear(s.getMinYear())
                .maxYear(s.getMaxYear())
                .minMileage(s.getMinMileage())
                .maxMileage(s.getMaxMileage())
                .build();
    }

    public SubscriptionListDTO convertSubscriptionToListDTO(Subscription s) {

        SubscriptionListDTO sub = SubscriptionListDTO.builder()
                .name(s.getName())
                .subId(s.getSubId())
                .bodyType(s.getBodyType())
                .fuelType(s.getFuelType())
                .color(s.getColor())
                .barterOption(s.getBarterOption())
                .loanOption(s.getLoanOption()).leaseOption(s.getLeaseOption())
                .cashOption(s.getCashOption()).creationDate(s.getCreationDate())
                .minPrice(s.getMinPrice()).maxPrice(s.getMaxPrice())
                .minYear(s.getMinYear()).maxYear(s.getMaxYear())
                .minMileage(s.getMinMileage()).maxMileage(s.getMaxMileage())
                .build();
        sub.setSpecs(mapList(s.getSpecs(), CarSpecDTO.class));
        sub.setCity(map(s.getCity(), CityDTO.class));
        sub.setModel(map(s.getModel(), ModelDTO.class));
        sub.setMake(map(s.getMake(), MakeDTO.class));

        return sub;
    }
}