package az.code.carlada.daos;

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
    public List<Listing> searchAllListings(Subscription sub) {
        Long makeId = sub.getMake() == null ? null : sub.getMake().getId();
        Long modelId = sub.getModel() == null ? null : sub.getModel().getId();
        Long cityId = sub.getCity() == null ? null : sub.getCity().getId();

        Specification<Listing> spec = Specification
                .where(listingSpecs.equalMake(makeId))
                .and(listingSpecs.equalModel(modelId))
                .and(listingSpecs.equalLocation(cityId))
                .and(listingSpecs.equalFuelType(sub.getFuelType()))
                .and(listingSpecs.equalBodyType(sub.getBodyType()))
                .and(listingSpecs.betweenYears(sub.getMinYear(), sub.getMaxYear()))
                .and(listingSpecs.betweenPrices(sub.getMinPrice(), sub.getMaxPrice()))
                .and(listingSpecs.betweenMileages(sub.getMinMileage(), sub.getMaxMileage()))
                .and(listingSpecs.equalLoanOption(sub.getLoanOption()))
                .and(listingSpecs.equalBarterOption(sub.getBarterOption()))
                .and(listingSpecs.equalCashOption(sub.getCashOption()))
                .and(listingSpecs.equalLeaseOption(sub.getLeaseOption()));

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
                .and(subSpecs.equalBarterOption(list.getCar().getBarterOption()))
                .and(subSpecs.equalLoanOption(list.getCar().getLoanOption()))
                .and(subSpecs.equalCashOption(list.getCar().getCashOption()))
                .and(subSpecs.equalLeaseOption(list.getCar().getLeaseOption()));

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
