package az.code.carlada.services;

import az.code.carlada.dtos.ListingGetDTO;
import az.code.carlada.enums.Status;
import az.code.carlada.models.Listing;

public interface PaymentService {
    Listing payForListingStatus(Long listingId, String statusType, String username);
}
