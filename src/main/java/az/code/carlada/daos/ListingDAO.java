package az.code.carlada.daos;

import az.code.carlada.models.Listing;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ListingDAO {

    Page<Listing> getListingsByActive(Integer page,Integer count,boolean isActive);
    Page<Listing> getAllVipListing(Integer page,Integer count);
    Page<Listing> getAllListingByUsername(String username,Integer page,Integer count);
    Listing getListingByUsernameById(String username, Long id);
    Listing getListingById(Long id);
    Listing createListing(Listing listing);
    void saveListing(Listing listing);
    void delete(long id);
    List<Listing> getWaitingExpired();
}