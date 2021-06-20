package az.code.carlada.daos;

import az.code.carlada.dtos.ListingListDTO;
import az.code.carlada.dtos.SearchDTO;
import az.code.carlada.models.Listing;
import az.code.carlada.models.Subscription;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SearchDAO {
    List<Listing> searchAllListings(Subscription sub);
    Page<Listing> searchListingsByPage(SearchDTO params);
}
