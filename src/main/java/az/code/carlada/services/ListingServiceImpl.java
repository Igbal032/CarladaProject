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
    public PaginationDTO<ListingListDTO> getListingsByActive(Integer page, Integer count, boolean isActive) {
        Page<Listing> p = listingDAO.getListingsByActive(page, count, isActive);
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
        Listing list = listingDAO.createListing(mapperService.convertLintingCreationToListing(listingCreationDTO));
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