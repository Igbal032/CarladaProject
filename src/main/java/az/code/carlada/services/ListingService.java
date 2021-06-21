package az.code.carlada.services;


import az.code.carlada.dtos.ListingCreationDTO;
import az.code.carlada.dtos.ListingGetDTO;
import az.code.carlada.dtos.ListingListDTO;
import az.code.carlada.dtos.PaginationDTO;

public interface ListingService {
    PaginationDTO<ListingListDTO> getAllListing(Integer page, Integer count);
    PaginationDTO<ListingListDTO> getAllVipListing(Integer page, Integer count);
    ListingGetDTO getListingById(Long id);
    PaginationDTO<ListingListDTO> getAllListingBySlug(String username, Integer page, Integer count);
    PaginationDTO<ListingListDTO> getAllListingByProfile(Integer page, Integer count);
    ListingGetDTO getListingByIdByProfile(Long id);
    ListingGetDTO saveListing(ListingCreationDTO listingCreationDTO);
    void delete(long id);
}
