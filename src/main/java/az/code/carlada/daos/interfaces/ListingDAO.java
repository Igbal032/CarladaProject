package az.code.carlada.daos.interfaces;

import az.code.carlada.models.AppUser;
import az.code.carlada.models.Image;
import az.code.carlada.models.Listing;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ListingDAO {

    Page<Listing> getListingsByActive(Integer page, Integer count, boolean isActive);

    List<Listing> getAllActiveListingsByUser(AppUser appUser);

    Page<Listing> getAllVipListing(Integer page, Integer count);

    Page<Listing> getAllListingByUsername(String username, Integer page, Integer count);

    Listing getListingByUsernameById(String username, Long id);

    Listing getListingById(Long id);

    Optional<Listing> getListingsByAppUser(AppUser appUser);

    Listing createListing(Listing listing, AppUser appUser);

    void saveListing(Listing listing);

    void delete(long id);

    List<Listing> getWaitingExpired();
}