package az.code.carlada.components;


import az.code.carlada.daos.interfaces.DictionaryDAO;
import az.code.carlada.daos.interfaces.TransactionDAO;
import az.code.carlada.daos.interfaces.UserDAO;
import az.code.carlada.dtos.*;
import az.code.carlada.enums.*;
import az.code.carlada.models.*;
import az.code.carlada.utils.BasicUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

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
    public UserDAO userDAO;
    public TransactionDAO transactionDAO;

    public ModelMapperComponent(ModelMapper modelMapper, DictionaryDAO dictionaryDAO, UserDAO userDAO, TransactionDAO transactionDAO) {
        this.modelMapper = modelMapper;
        this.dictionaryDAO = dictionaryDAO;
        this.userDAO = userDAO;
        this.transactionDAO = transactionDAO;
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


    public Listing convertListingCreationToListing(ListingCreationDTO listingCreationDTO, AppUser appUser) {
        DictionaryDAO<Model> modelDAO = dictionaryDAO;
        DictionaryDAO<City> cityDAO = dictionaryDAO;
        CarDetail carDetail = CarDetail.builder()
                .bodyType(BasicUtil.getEnumFromString(BodyType.class, listingCreationDTO.getBodyType()))
                .color(BasicUtil.getEnumFromString(Color.class, listingCreationDTO.getColor()))
                .fuelType(BasicUtil.getEnumFromString(FuelType.class, listingCreationDTO.getFuelType()))
                .gearBox(BasicUtil.getEnumFromString(Gearbox.class, listingCreationDTO.getGearBox()))
                .carSpecifications(dictionaryDAO.findAllDataByIds(listingCreationDTO.getCarSpecIds(), Specification.class))
                .build();
        Car car = Car.builder()
                .model(modelDAO.findDataById(listingCreationDTO.getModelId(), Model.class))
                .year(listingCreationDTO.getYear())
                .price(listingCreationDTO.getPrice())
                .mileage(listingCreationDTO.getMileage())
                .carDetail(carDetail)
                .build();
        Status status = transactionDAO.getStatusByName(listingCreationDTO.getType());
        Listing listing = Listing.builder()
                .id(listingCreationDTO.getId())
                .isActive(true)
                .loanOption(listingCreationDTO.getCreditOption())
                .barterOption(listingCreationDTO.getBarterOption())
                .leaseOption(listingCreationDTO.getLeaseOption())
                .cashOption(listingCreationDTO.getCashOption())
                .description(listingCreationDTO.getDescription())
                .appUser(appUser)
                .statusType(status)
                .city(cityDAO.findDataById(listingCreationDTO.getCityId(), City.class))
                .car(car)
                .autoPay(listingCreationDTO.getAuto_pay())
                .updatedAt(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .expiredAt(LocalDateTime.now().plusDays(30))
                .build();
        car.setListing(listing);
        carDetail.setCar(car);
        return listing;
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
                .type(i.getStatusType().getStatusName())
                .year(i.getCar().getYear())
                .autoPay(i.isAutoPay())
                .carSpecs(mapList(i.getCar().getCarDetail().getCarSpecifications(), CarSpecDTO.class))
                .bodyType(i.getCar().getCarDetail().getBodyType().name())
                .user(map(i.getAppUser(), AppUserDTO.class))
                .city(map(i.getCity(), CityDTO.class))
                .color(i.getCar().getCarDetail().getColor().toString())
                .cashOption(i.isCashOption())
                .creditOption(i.isLoanOption())
                .barterOption(i.isBarterOption())
                .leaseOption(i.isLeaseOption())
                .fuelType(i.getCar().getCarDetail().getFuelType().toString())
                .description(i.getDescription())
                .gearBox(i.getCar().getCarDetail().getGearBox().toString())
                .model(map(i.getCar().getModel(), ModelDTO.class))
                .make(map(i.getCar().getModel().getMake(), MakeDTO.class))
                .isActive(i.isActive())
                .mileage(i.getCar().getMileage())
                .price(i.getCar().getPrice())
                .thumbnailUrl(i.getThumbnailUrl())
                .updatedAt(i.getUpdatedAt())
                .build();
    }


    public Subscription convertDTOToSubscription(SubscriptionDTO s) {
        DictionaryDAO<Model> modelDAO = dictionaryDAO;
        DictionaryDAO<City> cityDAO = dictionaryDAO;
        DictionaryDAO<Make> makeDAO = dictionaryDAO;
        return Subscription.builder()
                .name(s.getName())
                .subId(s.getSubId())
                .make(makeDAO.findDataById(s.getMakeId(), Make.class))
                .model(modelDAO.findDataById(s.getModelId(), Model.class))
                .city(cityDAO.findDataById(s.getCityId(), City.class))
                .specs(dictionaryDAO.findAllDataByIds(s.getSpecs(), Specification.class))
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
