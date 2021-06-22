package az.code.carlada.daos;

import az.code.carlada.models.Listing;
import org.springframework.data.domain.Page;

public interface ListingDAO {

    Page<Listing> getAllListing(Integer page,Integer count);
    Page<Listing> getAllVipListing(Integer page,Integer count);
    Page<Listing> getAllListingByUsername(String username,Integer page,Integer count);
    Listing getListingByUsernameById(String username, Long id);
    Listing getListingById(Long id);
    Listing createListing(Listing listing);
    void saveListing(Listing listing);
    void delete(long id);
}