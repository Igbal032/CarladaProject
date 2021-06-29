package az.code.carlada.services.interfaces;

import az.code.carlada.dtos.ListingListDTO;
import az.code.carlada.dtos.PaginationDTO;

import java.util.Map;

public interface SearchService {
    PaginationDTO<ListingListDTO> searchListings(Map<String, String> params);
}
