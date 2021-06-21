package az.code.carlada.daos;

import az.code.carlada.dtos.ListingCreationDTO;
import az.code.carlada.dtos.ListingGetDTO;
import az.code.carlada.dtos.ListingListDTO;
import az.code.carlada.models.Listing;

import java.util.List;

public interface ListingDAO {

    List<Listing> getAllListing();
    List<Listing> getAllVipListing();
    List<Listing> getAllListingByUsername(String username);
    Listing getAllListingByUsernameById(String username, Long id);
    Listing getListingById(Long id);
    Listing createListing(Listing listing);
    void delete(long id);
}