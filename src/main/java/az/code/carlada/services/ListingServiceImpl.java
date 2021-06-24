package az.code.carlada.services;


import az.code.carlada.components.ModelMapperComponent;
import az.code.carlada.components.SchedulerExecutorComponent;
import az.code.carlada.daos.interfaces.ListingDAO;
import az.code.carlada.daos.interfaces.TransactionDAO;
import az.code.carlada.daos.interfaces.UserDAO;
import az.code.carlada.dtos.*;
import az.code.carlada.models.*;
import az.code.carlada.services.interfaces.ListingService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ListingServiceImpl implements ListingService {

    ListingDAO listingDAO;
    UserDAO userDAO;
    TransactionDAO transactionDAO;
    ModelMapperComponent mapperService;
    SchedulerExecutorComponent schExecService;

    public ListingServiceImpl(ListingDAO listingDAO, UserDAO userDAO, TransactionDAO transactionDAO, ModelMapperComponent mapperService, SchedulerExecutorComponent schExecService) {
        this.listingDAO = listingDAO;
        this.userDAO = userDAO;
        this.transactionDAO = transactionDAO;
        this.mapperService = mapperService;
        this.schExecService = schExecService;
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
    public PaginationDTO<ListingListDTO> getAllListingByProfile(Integer page, Integer count, String username) {
        return getAllListingBySlug(username, page, count);
    }

    @Override
    public ListingGetDTO getListingByIdByProfile(Long id, String username) {
        return mapperService.convertListingToListingGetDto(listingDAO.getListingByUsernameById(username, id));
    }

    @Override
    public ListingGetDTO saveListing(ListingCreationDTO listingCreationDTO, String username) {
        AppUser user = userDAO.getUserByUsername(username);
        List<Listing> listings = listingDAO.getAllActiveListingsByUser(user);
        Listing listing = mapperService.convertListingCreationToListing(listingCreationDTO, user);
        Optional<Listing> checkListing = listings.stream()
                .filter(w -> w.getStatusType().getStatusName().equals("FREE") && w.isActive()).findFirst();
        Status status;
        if (checkListing.isPresent()) {
            status = transactionDAO.getStatusByName("STANDARD");
        } else
            status = transactionDAO.getStatusByName("FREE");
        listing.setStatusType(status);
        Listing listing1 = listingDAO.createListing(listing, username);
        transactionDAO.payForListingStatus(listing1.getId(), status.getStatusName(), username);
        schExecService.runSubscriptionJob(listing1);
        return mapperService.convertListingToListingGetDto(listing1);
    }

    @Override
    public void delete(long id, String username) {
        listingDAO.delete(id);
    }
}