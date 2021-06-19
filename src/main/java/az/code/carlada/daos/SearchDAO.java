package az.code.carlada.daos;

import az.code.carlada.dtos.ListingListDTO;
import az.code.carlada.dtos.SearchDTO;
import az.code.carlada.models.Listing;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SearchDAO {
    List<Listing> searchListings(SearchDTO params);
    Page<Listing> searchListingsByPage(SearchDTO params);
}
