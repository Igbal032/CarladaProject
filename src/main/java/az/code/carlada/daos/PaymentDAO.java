package az.code.carlada.daos;

import az.code.carlada.enums.Status;
import az.code.carlada.models.Listing;

public interface PaymentDAO {
    Listing payForListingStatus(Long listingId, Status statusType, String username);
}
