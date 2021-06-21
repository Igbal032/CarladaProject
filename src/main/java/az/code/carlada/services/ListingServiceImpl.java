package az.code.carlada.services;

import az.code.carlada.daos.ListingDAO;
import az.code.carlada.dtos.ListingCreationDTO;
import az.code.carlada.dtos.ListingGetDTO;
import az.code.carlada.dtos.ListingListDTO;
import az.code.carlada.dtos.PaginationDTO;
import az.code.carlada.enums.*;
import az.code.carlada.models.*;
import az.code.carlada.repositories.*;
import az.code.carlada.utils.BasicUtil;
import az.code.carlada.utils.ModelMapperUtil;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
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
    public PaginationDTO<ListingListDTO> getAllListing(Integer page, Integer count) {
        Page<Listing> p = listingDAO.getAllListing(page, count);
        List<ListingListDTO> listingListDTOS = p.getContent().stream()
                .map(i -> mapperUtil.convertListingToListDto(i))
                .collect(Collectors.toList());

        return new PaginationDTO<>(p.hasNext(), p.hasPrevious(), p.getTotalPages(), p.getNumber(), p.getTotalElements(), listingListDTOS);
    }

    @Override
    public PaginationDTO<ListingListDTO> getAllVipListing(Integer page, Integer count) {
        Page<Listing> p = listingDAO.getAllVipListing(page, count);
        List<ListingListDTO> listingListDTOS = p.getContent()
                .stream().map(i -> mapperUtil.convertListingToListDto(i))
                .collect(Collectors.toList());

        return new PaginationDTO<>(p.hasNext(), p.hasPrevious(), p.getTotalPages(), p.getNumber(), p.getTotalElements(), listingListDTOS);
    }

    @Override
    public ListingGetDTO getListingById(Long id) {
        return mapperUtil.convertListingToListingGetDto(listingDAO.getListingById(id));
    }

    @Override
    public PaginationDTO<ListingListDTO> getAllListingBySlug(String username, Integer page, Integer count) {
        Page<Listing> p = listingDAO.getAllListingByUsername(username, page, count);
        List<ListingListDTO> listingListDTOS = p.getContent().stream()
                .map(i -> mapperUtil.convertListingToListDto(i))
                .collect(Collectors.toList());
        return new PaginationDTO<>(p.hasNext(), p.hasPrevious(), p.getTotalPages(), p.getNumber(), p.getTotalElements(), listingListDTOS);
    }

    @Override
    public PaginationDTO<ListingListDTO> getAllListingByProfile(Integer page, Integer count) {
        String username = "shafig";
        return getAllListingBySlug(username, page, count);
    }

    @Override
    public ListingGetDTO getListingByIdByProfile(Long id) {
        String username = "shafig";
        return mapperUtil.convertListingToListingGetDto(listingDAO.getListingByUsernameById(username, id));
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
                .id(listingCreationDTO.getId())
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