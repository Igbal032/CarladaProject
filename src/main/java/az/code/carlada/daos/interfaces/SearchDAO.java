package az.code.carlada.daos.interfaces;

import az.code.carlada.dtos.ListingListDTO;
import az.code.carlada.dtos.SearchDTO;
import az.code.carlada.models.Listing;
import az.code.carlada.models.Subscription;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SearchDAO {
    List<Listing> searchAllListingsByExpireDate();
    List<Listing> searchAllListingsWithExpiredDate();
    List<Subscription> searchAllSubscriptions(Listing list);
    Page<Listing> searchListingsByPage(SearchDTO params);
}
