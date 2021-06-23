package az.code.carlada.services;


import az.code.carlada.components.ModelMapperComponent;
import az.code.carlada.configs.SchedulerExecutorConfig;
import az.code.carlada.daos.DictionaryDAO;
import az.code.carlada.daos.ListingDAO;
import az.code.carlada.daos.UserDAO;
import az.code.carlada.dtos.*;
import az.code.carlada.enums.*;
import az.code.carlada.models.*;
import az.code.carlada.utils.BasicUtil;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListingServiceImpl implements ListingService {

    ListingDAO listingDAO;
    ModelMapperComponent mapperService;
    DictionaryDAO dictionaryDAO;
    UserDAO userDAO;
    SchedulerExecutorConfig schExecService;
    ImageService imageService;

    public ListingServiceImpl(ListingDAO listingDAO, ModelMapperComponent mapperService, DictionaryDAO dictionaryDAO, UserDAO userDAO, SchedulerExecutorConfig schExecService, ImageService imageService) {
        this.listingDAO = listingDAO;
        this.mapperService = mapperService;
        this.dictionaryDAO = dictionaryDAO;
        this.userDAO = userDAO;
        this.schExecService = schExecService;
        this.imageService = imageService;
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

        Model model = dictionaryDAO.findModelById(listingCreationDTO.getModelId());
        Make make = dictionaryDAO.findMakeById(listingCreationDTO.getMakeId());
        City city = dictionaryDAO.findCityById(listingCreationDTO.getCityId());
        AppUser appUser = userDAO.getUserByUsername("elgunvm");

        model.setMake(make);
        CarDetail carDetail = CarDetail.builder()
                .bodyType(BasicUtil.getEnumFromString(BodyType.class, listingCreationDTO.getBodyType()))
                .color(BasicUtil.getEnumFromString(Color.class, listingCreationDTO.getColor()))
                .fuelType(BasicUtil.getEnumFromString(FuelType.class, listingCreationDTO.getFuelType()))
                .gearBox(BasicUtil.getEnumFromString(Gearbox.class, listingCreationDTO.getGearBox()))
                .carSpecifications(dictionaryDAO.findAllSpecificationById(listingCreationDTO.getCarSpecIds()))
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
        Listing list = listingDAO.createListing(listing);
        schExecService.runSubscriptionJob(list);
        return mapperService.convertListingToListingGetDto(list);
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