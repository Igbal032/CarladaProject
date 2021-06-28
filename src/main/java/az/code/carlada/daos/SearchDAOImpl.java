package az.code.carlada.daos;

import az.code.carlada.daos.interfaces.SearchDAO;
import az.code.carlada.dtos.SearchDTO;
import az.code.carlada.models.Listing;
import az.code.carlada.models.Subscription;
import az.code.carlada.repositories.ListingRepo;
import az.code.carlada.repositories.SubscriptionRepo;
import az.code.carlada.components.ListingSpecificationComponent;
import az.code.carlada.components.SubscriptionSpecificationComponent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class SearchDAOImpl implements SearchDAO {
    ListingRepo listingRepo;
    SubscriptionRepo subRepo;
    ListingSpecificationComponent listingSpecs;
    SubscriptionSpecificationComponent subSpecs;

    public SearchDAOImpl(ListingRepo listingRepo, SubscriptionRepo subRepo, ListingSpecificationComponent listingSpecs, SubscriptionSpecificationComponent subSpecs) {
        this.listingRepo = listingRepo;
        this.subRepo = subRepo;
        this.listingSpecs = listingSpecs;
        this.subSpecs = subSpecs;
    }


    @Override
    public List<Listing> searchAllListingsByExpireDate() {
        Specification<Listing> spec = Specification
                .where((root, query, cb) ->
                                cb.and(cb.lessThanOrEqualTo(root.get("expiredAt"), LocalDateTime.now().plusDays(1)),
                                        cb.isTrue(root.get("isActive"))));
        return listingRepo.findAll(spec);
    }
    @Override
    public List<Listing> searchAllListingsWithExpiredDate() {
        org.springframework.data.jpa.domain.Specification<Listing> spec = Specification
                .where((root, query, cb) ->
                        cb.and(cb.lessThanOrEqualTo(root.get("expiredAt"), LocalDateTime.now()),
                                cb.isTrue(root.get("isActive")),cb.isTrue(root.get("autoPay"))));
        return listingRepo.findAll(spec);
    }

    @Override
    public List<Subscription> searchAllSubscriptions(Listing list) {
        Long makeId = list.getCar().getModel().getMake().getId();
        Long modelId = list.getCar().getModel().getId();
        Long cityId = list.getCity().getId();

        Specification<Subscription> spec = Specification
                .where(subSpecs.equalMake(makeId))
                .and(subSpecs.equalModel(modelId))
                .and(subSpecs.equalLocation(cityId))
                .and(subSpecs.equalFuelType(list.getCar().getCarDetail().getFuelType()))
                .and(subSpecs.equalBodyType(list.getCar().getCarDetail().getBodyType()))
                .and(subSpecs.betweenIntegers(list.getCar().getMileage(), "minMileage", "maxMileage"))
                .and(subSpecs.betweenIntegers(list.getCar().getPrice(), "minPrice", "maxPrice"))
                .and(subSpecs.betweenIntegers(list.getCar().getYear(), "minYear", "maxYear"))
                .and(subSpecs.equalBarterOption(list.getBarterOption()))
                .and(subSpecs.equalLoanOption(list.getLoanOption()))
                .and(subSpecs.equalCashOption(list.getCashOption()))
                .and(subSpecs.equalLeaseOption(list.getLeaseOption()));

        return subRepo.findAll(spec);
    }

    @Override
    public Page<Listing> searchListingsByPage(SearchDTO params) {
        Pageable pageable = PageRequest.of(params.getPage(), params.getCount());

        Specification<Listing> spec = Specification
                .where(listingSpecs.equalMake(params.getMake()))
                .and(listingSpecs.equalModel(params.getModel()))
                .and(listingSpecs.equalLocation(params.getLocation()))
                .and(listingSpecs.equalFuelType(params.getFuelType()))
                .and(listingSpecs.equalBodyType(params.getBodyType()))
                .and(listingSpecs.betweenYears(params.getMinYear(), params.getMaxYear()))
                .and(listingSpecs.betweenPrices(params.getMinPrice(), params.getMaxPrice()))
                .and(listingSpecs.betweenMileages(params.getMinMileage(), params.getMaxMileage()))
                .and(listingSpecs.equalLoanOption(params.getLoanOption()))
                .and(listingSpecs.equalBarterOption(params.getBarterOption()))
                .and(listingSpecs.equalCashOption(params.getCashOption()))
                .and(listingSpecs.equalLeaseOption(params.getLeaseOption()));

        return listingRepo.findAll(spec, pageable);
    }
}
