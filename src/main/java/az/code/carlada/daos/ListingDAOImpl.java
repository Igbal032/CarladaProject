package az.code.carlada.daos;

import az.code.carlada.daos.interfaces.ListingDAO;
import az.code.carlada.daos.interfaces.UserDAO;
import az.code.carlada.exceptions.EnoughBalanceException;
import az.code.carlada.exceptions.ListingNotFound;
import az.code.carlada.models.*;
import az.code.carlada.repositories.ListingRepo;
import az.code.carlada.repositories.StatusRepo;
import az.code.carlada.repositories.TransactionRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class ListingDAOImpl implements ListingDAO {

    ListingRepo listingRepository;
    StatusRepo statusRepo;
    TransactionRepo transactionRepo;
    UserDAO userDAO;
    @Value("${app.status.initial}")
    String initial;

    public ListingDAOImpl(ListingRepo listingRepository, StatusRepo statusRepo, TransactionRepo transactionRepo, UserDAO userDAO) {
        this.listingRepository = listingRepository;
        this.statusRepo = statusRepo;
        this.transactionRepo = transactionRepo;
        this.userDAO = userDAO;
    }

    @Override
    public Page<Listing> getListingsByActive(Integer page, Integer count, boolean isActive) {
        Pageable pageable = PageRequest.of(page, count);
        Specification<Listing> spec = Specification
                .where((root, query, cb) -> cb.equal(root.get("isActive"), isActive));
        return listingRepository.findAll(spec, pageable);
    }

    @Override
    public List<Listing> getAllActiveListingsByUser(AppUser appUser) {
        Specification<Listing> spec = Specification
                .where((root, query, cb) -> cb.and(cb.equal(root.get("isActive"), true),
                        cb.equal(root.get("appUser"), appUser)));
        return listingRepository.findAll(spec);
    }

    @Override
    public Page<Listing> getAllVipListing(Integer page, Integer count) {
        Pageable pageable = PageRequest.of(page, count);
        Status status = statusRepo.getStatusByStatusName(initial);
        return listingRepository.getAllByStatusType(status, pageable);
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
    public Listing createListing(Listing listing, AppUser user) {
        if(listing.getStatusType().getStatusName().equals(initial)){}
        else if (user.getAmount()<listing.getStatusType().getPrice())
            throw new EnoughBalanceException("Balance is not enough");
        Listing listing1 = listingRepository.save(listing);
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
    public List<Listing> getWaitingExpired() {
        return listingRepository.getWaitingExpired();
    }
}