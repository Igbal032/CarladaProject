package az.code.carlada.daos;

import az.code.carlada.enums.Status;
import az.code.carlada.enums.TransactionType;
import az.code.carlada.exceptions.ListingNotFound;
import az.code.carlada.models.Listing;
import az.code.carlada.models.Transaction;
import az.code.carlada.repositories.ListingRepo;
import az.code.carlada.repositories.TransactionRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class ListingDAOImpl implements ListingDAO {

    ListingRepo listingRepository;
    TransactionRepo transactionRepo;

    public ListingDAOImpl(ListingRepo listingRepository, TransactionRepo transactionRepo) {
        this.listingRepository = listingRepository;
        this.transactionRepo = transactionRepo;
    }

    @Override
    public Page<Listing> getAllListing(Integer page, Integer count) {
        Pageable pageable = PageRequest.of(page, count);
        return listingRepository.findAll(pageable);
    }

    @Override
    public Page<Listing> getAllVipListing(Integer page, Integer count) {
        Pageable pageable = PageRequest.of(page, count);
        return listingRepository.getAllByTypeEquals(Status.VIP, pageable);
    }

    @Override
    public Page<Listing> getAllListingByUsername(String username, Integer page, Integer count) {
        Pageable pageable = PageRequest.of(page, count);
        return listingRepository.getAllByAppUserUsername(username, pageable);
    }

    @Override
    public Listing getListingByUsernameById(String username, Long id) {
        Listing listing = listingRepository.getListingByAppUserUsernameAndId(username, id);
        if (listing == null) {
            throw new ListingNotFound("Not such a listing");
        }
        return listingRepository.getListingByAppUserUsernameAndId(username, id);
    }

    @Override
    public Listing getListingById(Long id) {
        Optional<Listing> listing = listingRepository.findById(id);
        if (listing.isEmpty())
            throw new ListingNotFound("Not such a listing");
        return listing.get();
    }

    @Override
    public Listing createListing(Listing listing) {
        Listing listing1 = listingRepository.save(listing);
        transactionRepo.save(Transaction.builder()
                .amount(Status.FREE.getStatusAmount())
                .listingId(listing1.getId())
                .createdDate(LocalDateTime.now())
                .transactionType(TransactionType.NEW)
                .appUser(listing.getAppUser())
                .build());
        return listing1;
    }

    @Override
    public void saveListing(Listing listing) {
        listingRepository.save(listing);
    }

    @Override
    public void delete(long id) {
        if (listingRepository.findById(id).isEmpty())
            throw new ListingNotFound("Not such a listing");
        else
            listingRepository.deleteById(id);
    }
    @Override
    public void disableExpired() {
        listingRepository.disableExpired();
    }
}