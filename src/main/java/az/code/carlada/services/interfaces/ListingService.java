package az.code.carlada.services.interfaces;


import az.code.carlada.dtos.ListingCreationDTO;
import az.code.carlada.dtos.ListingGetDTO;
import az.code.carlada.dtos.ListingListDTO;
import az.code.carlada.dtos.PaginationDTO;
import az.code.carlada.models.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ListingService {
    PaginationDTO<ListingListDTO> getListingsByActive(Integer page, Integer count, boolean isActive);

    PaginationDTO<ListingListDTO> getAllVipListing(Integer page, Integer count);

    ListingGetDTO getListingById(Long id);

    PaginationDTO<ListingListDTO> getAllListingBySlug(String username, Integer page, Integer count);

    PaginationDTO<ListingListDTO> getAllListingByProfile(Integer page, Integer count, String username);

    ListingGetDTO getListingByIdByProfile(Long id, String username);

    ListingGetDTO saveListing(ListingCreationDTO listingCreationDTO, String username);

    void delete(long id, String username);

}