package az.code.carlada.daos;

import az.code.carlada.enums.Status;
import az.code.carlada.enums.TransactionType;
import az.code.carlada.exceptions.ListingNotFound;
import az.code.carlada.models.*;
import az.code.carlada.repositories.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class ListingDaoImpl implements ListingDAO {

    ListingRepo listingRepository;
    TransactionRepo transactionRepo;
    ModelRepo modelRepository;
    MakeRepo makeRepository;
    CityRepo cityRepository;
    SpecificationRepo specRepository;
    UserRepo userRepository;

    public ListingDaoImpl(ListingRepo listingRepository, TransactionRepo transactionRepo, ModelRepo modelRepository, MakeRepo makeRepository, CityRepo cityRepository, SpecificationRepo specRepository, UserRepo userRepository) {
        this.listingRepository = listingRepository;
        this.transactionRepo = transactionRepo;
        this.modelRepository = modelRepository;
        this.makeRepository = makeRepository;
        this.cityRepository = cityRepository;
        this.specRepository = specRepository;
        this.userRepository = userRepository;
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
        System.out.println(listing);
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
    public void delete(long id) {
        if (listingRepository.findById(id).isEmpty())
            throw new ListingNotFound("Not such a listing");
        else
            listingRepository.deleteById(id);
    }

    @Override
    public Optional<Model> getModelById(Long id){
        return modelRepository.findById(id);
    }

    @Override
    public Optional<Make> getMakeById(Long id) {
        return makeRepository.findById(id);
    }

    @Override
    public Optional<City> getCityById(Long id) {
        return cityRepository.findById(id);
    }

    @Override
    public Optional<AppUser> getUserByUsername(String username) {
        return userRepository.getAppUserByUsername(username);
    }

    @Override
    public List<Specification> getAllSpecsById(Iterable<Long> id) {
        return specRepository.findAllById(id);
    }
}
