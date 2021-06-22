package az.code.carlada.services;


import az.code.carlada.dtos.ListingCreationDTO;
import az.code.carlada.dtos.ListingGetDTO;
import az.code.carlada.dtos.ListingListDTO;
import az.code.carlada.dtos.PaginationDTO;
import az.code.carlada.models.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ListingService {
    PaginationDTO<ListingListDTO> getAllListing(Integer page, Integer count);
    PaginationDTO<ListingListDTO> getAllVipListing(Integer page, Integer count);
    ListingGetDTO getListingById(Long id);
    PaginationDTO<ListingListDTO> getAllListingBySlug(String username, Integer page, Integer count);
    PaginationDTO<ListingListDTO> getAllListingByProfile(Integer page, Integer count);
    ListingGetDTO getListingByIdByProfile(Long id);
    ListingGetDTO saveListing(ListingCreationDTO listingCreationDTO);
    void delete(long id);
    Image setThumbnailForListing(Long listingId, MultipartFile file) throws IOException;
}