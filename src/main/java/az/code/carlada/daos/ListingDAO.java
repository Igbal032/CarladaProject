package az.code.carlada.daos;

import az.code.carlada.models.*;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ListingDAO {

    Page<Listing> getAllListing(Integer page,Integer count);
    Page<Listing> getAllVipListing(Integer page,Integer count);
    Page<Listing> getAllListingByUsername(String username,Integer page,Integer count);
    Listing getListingByUsernameById(String username, Long id);
    Listing getListingById(Long id);
    Listing createListing(Listing listing);
    void delete(long id);
    Optional<Model> getModelById(Long id);
    Optional<Make> getMakeById(Long id);
    Optional<City> getCityById(Long id);
    Optional<AppUser> getUserByUsername(String username);
    List<Specification> getAllSpecsById(Iterable<Long> id);
}