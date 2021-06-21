package az.code.carlada.services;


import az.code.carlada.dtos.ListingCreationDTO;
import az.code.carlada.dtos.ListingGetDTO;
import az.code.carlada.dtos.ListingListDTO;
import az.code.carlada.models.Listing;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ListingService {
    List<ListingListDTO> getAllListing();
    List<ListingListDTO> getAllVipListing();
    ListingGetDTO getListingById(Long id);
    List<ListingListDTO> getAllListingBySlug(String username);
    List<ListingListDTO> getAllListingByProfile();
    ListingGetDTO getListingByIdByProfile(Long id);
    ListingGetDTO saveListing(ListingCreationDTO listingCreationDTO);
    void delete(long id);
    String makeListVip(long id);
    String makeListPaid(long id);
}
