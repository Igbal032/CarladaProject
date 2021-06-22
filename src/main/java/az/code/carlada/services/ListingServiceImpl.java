package az.code.carlada.services;

import az.code.carlada.daos.ImageDAO;
import az.code.carlada.daos.ListingDAO;
import az.code.carlada.dtos.*;
import az.code.carlada.enums.*;
import az.code.carlada.exceptions.UserNotFound;
import az.code.carlada.models.*;
import az.code.carlada.repositories.*;
import az.code.carlada.utils.BasicUtil;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ListingServiceImpl implements ListingService {

    ListingDAO listingDAO;
    ImageDAO imageDAO;
    ImageService imageService;
    ModelMapperService mapperService;
    ListingRepo listingRepository;
    ModelRepo modelRepository;
    MakeRepo makeRepository;
    CityRepo cityRepository;
    SpecificationRepo specRepository;
    UserRepo userRepository;

    public ListingServiceImpl(ListingDAO listingDAO, ImageDAO imageDAO, ImageService imageService, ModelMapperService mapperService, ListingRepo listingRepository, ModelRepo modelRepository, MakeRepo makeRepository, CityRepo cityRepository, SpecificationRepo specRepository, UserRepo userRepository) {
        this.listingDAO = listingDAO;
        this.imageDAO = imageDAO;
        this.imageService = imageService;
        this.mapperService = mapperService;
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
                .map(i -> mapperService.convertListingToListDto(i))
                .collect(Collectors.toList());

        return new PaginationDTO<>(p.hasNext(), p.hasPrevious(), p.getTotalPages(), p.getNumber(), p.getTotalElements(), listingListDTOS);
    }

    @Override
    public PaginationDTO<ListingListDTO> getAllVipListing(Integer page, Integer count) {
        Page<Listing> p = listingDAO.getAllVipListing(page, count);
        List<ListingListDTO> listingListDTOS = p.getContent()
                .stream().map(i -> mapperService.convertListingToListDto(i))
                .collect(Collectors.toList());

        return new PaginationDTO<>(p.hasNext(), p.hasPrevious(), p.getTotalPages(), p.getNumber(), p.getTotalElements(), listingListDTOS);
    }

    @Override
    public ListingGetDTO getListingById(Long id) {
        return mapperService.convertListingToListingGetDto(listingDAO.getListingById(id));
    }

    @Override
    public PaginationDTO<ListingListDTO> getAllListingBySlug(String username, Integer page, Integer count) {
        Page<Listing> p = listingDAO.getAllListingByUsername(username, page, count);
        List<ListingListDTO> listingListDTOS = p.getContent().stream()
                .map(i -> mapperService.convertListingToListDto(i))
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
        return mapperService.convertListingToListingGetDto(listingDAO.getListingByUsernameById(username, id));
    }

    @Override
    public ListingGetDTO saveListing(ListingCreationDTO listingCreationDTO) {
        Model model = modelRepository.getById(listingCreationDTO.getModelId());
        Make make = makeRepository.getById(listingCreationDTO.getMakeId());
        City city = cityRepository.getById(listingCreationDTO.getCityId());
        Optional<AppUser> appUser = userRepository.getAppUserByUsername("igbal-hasanli");
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

        System.out.println(listingCreationDTO.getAuto_pay());

        Listing listing = Listing.builder()
                .id(listingCreationDTO.getId())
                .isActive(true)
                .description(listingCreationDTO.getDescription())
                .appUser(appUser.get())
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
        return mapperService.convertListingToListingGetDto(listing);
    }

    @Override
    public void delete(long id) {
        listingDAO.delete(id);
    }

    @Override
    public Image setThumbnailForListing(Long listingId, MultipartFile file) throws IOException {
        Image image = imageService.addImgToListing(listingId, file);
        Listing listing = listingDAO.getListingById(listingId);
        listing.setThumbnailUrl(image.getName());
        listingDAO.saveListing(listing);
        return image;
    }
}