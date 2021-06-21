package az.code.carlada.services;

import az.code.carlada.daos.ListingDAO;
import az.code.carlada.dtos.ListingCreationDTO;
import az.code.carlada.dtos.ListingGetDTO;
import az.code.carlada.dtos.ListingListDTO;
import az.code.carlada.enums.*;
import az.code.carlada.exceptions.ListingNotFound;
import az.code.carlada.models.*;
import az.code.carlada.repositories.*;
import az.code.carlada.utils.BasicUtil;
import az.code.carlada.utils.ModelMapperUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListingServiceImpl implements ListingService {

    ListingDAO listingDAO;
    ModelMapperUtil mapperUtil;
    ListingRepo listingRepository;
    ModelRepo modelRepository;
    MakeRepo makeRepository;
    CityRepo cityRepository;
    SpecificationRepo specRepository;
    UserRepo userRepository;

    public ListingServiceImpl(ListingDAO listingDAO, ModelMapper modelMapper, ModelRepo modelRepository, ListingRepo listingRepository, MakeRepo makeRepository, CityRepo cityRepository, SpecificationRepo specRepository, UserRepo userRepository) {
        this.listingDAO = listingDAO;
        this.mapperUtil = ModelMapperUtil.builder().modelMapper(modelMapper).build();
        this.listingRepository = listingRepository;
        this.modelRepository = modelRepository;
        this.makeRepository = makeRepository;
        this.cityRepository = cityRepository;
        this.specRepository = specRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<ListingListDTO> getAllListing() {
        return listingDAO.getAllListing().stream().map(i->mapperUtil.convertListingToListDto(i)).collect(Collectors.toList());
    }

    @Override
    public List<ListingListDTO> getAllVipListing() {
        return listingDAO.getAllVipListing().stream().map(i->mapperUtil.convertListingToListDto(i)).collect(Collectors.toList());
    }

    @Override
    public ListingGetDTO getListingById(Long id) {
        return mapperUtil.convertListingToListingGetDto(listingDAO.getListingById(id));
    }

    @Override
    public List<ListingListDTO> getAllListingBySlug(String username) {
        return listingDAO.getAllListingByUsername(username).stream().map(i->mapperUtil.convertListingToListDto(i)).collect(Collectors.toList());
    }

    @Override
    public List<ListingListDTO> getAllListingByProfile() {
        String username = "shafig";
        return listingDAO.getAllListingByUsername(username).stream().map(i->mapperUtil.convertListingToListDto(i)).collect(Collectors.toList());
    }

    @Override
    public ListingGetDTO getListingByIdByProfile(Long id) {
        String username = "shafig";
        return mapperUtil.convertListingToListingGetDto(listingDAO.getAllListingByUsernameById(username, id));
    }

    @Override
    public ListingGetDTO saveListing(ListingCreationDTO listingCreationDTO) {
        Model model = modelRepository.getById(listingCreationDTO.getModelId());
        Make make = makeRepository.getById(listingCreationDTO.getMakeId());
        City city = cityRepository.getById(listingCreationDTO.getCityId());
        AppUser appUser = userRepository.getAppUserByUsername("shafig").get();
        model.setMake(make);
        CarDetail carDetail = CarDetail.builder()
                .bodyType(BasicUtil.getEnumFromString(BodyType.class, listingCreationDTO.getBodyType()))
                .color(BasicUtil.getEnumFromString(Color.class, listingCreationDTO.getColor()))
                .fuelType(BasicUtil.getEnumFromString(FuelType.class, listingCreationDTO.getFuelType()))
                .gearBox(BasicUtil.getEnumFromString(Gearbox.class, listingCreationDTO.getGearBox()))
                .carSpecifications(specRepository.findAllById(listingCreationDTO.getCarSpecIds()))
                .build();
        Car car = Car.builder()
                .model(model)
                .year(listingCreationDTO.getYear())
                .price(listingCreationDTO.getPrice())
                .mileage(listingCreationDTO.getMileage())
                .loanOption(listingCreationDTO.getCreditOption())
                .barterOption(listingCreationDTO.getBarterOption())
                .leaseOption(listingCreationDTO.getLeaseOption())
                .cashOption(listingCreationDTO.getCashOption())
                .carDetail(carDetail)
                .build();
        Listing listing = Listing.builder()
                .isActive(true)
                .description(listingCreationDTO.getDescription())
                .appUser(appUser)
                .type(BasicUtil.getEnumFromString(Status.class, listingCreationDTO.getType()))
                .city(city)
                .car(car)
                .autoPay(listingCreationDTO.getAuto_pay())
                .updatedAt(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .expiredAt(LocalDateTime.now().plusDays(30))
                .build();
        carDetail.setCar(car);
        car.setListing(listing);
        listingDAO.createListing(listing);
        return mapperUtil.convertListingToListingGetDto(listing);
    }

    @Override
    public void delete(long id) {
        listingDAO.delete(id);
    }


}