package az.code.carlada.daos;

import az.code.carlada.dtos.SearchDTO;
import az.code.carlada.models.Listing;
import az.code.carlada.models.Subscription;
import az.code.carlada.repositories.ListingRepo;
import az.code.carlada.services.SearchSpecificationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class SearchDAOImpl implements SearchDAO {
    ListingRepo listingRepo;
    SearchSpecificationService specs;

    public SearchDAOImpl(ListingRepo listingRepo, SearchSpecificationService specs) {
        this.listingRepo = listingRepo;
        this.specs = specs;
    }

    @Override
    public List<Listing> searchAllListings(Subscription sub) {
        Long makeId = sub.getMake() == null ? null : sub.getMake().getId();
        Long modelId = sub.getModel() == null ? null : sub.getModel().getId();
        Long cityId = sub.getCity() == null ? null : sub.getCity().getId();

        Specification<Listing> spec = Specification
                .where(specs.equalMake(makeId))
                .and(specs.equalModel(modelId))
                .and(specs.equalLocation(cityId))
                .and(specs.equalFuelType(sub.getFuelType()))
                .and(specs.equalBodyType(sub.getBodyType()))
                .and(specs.betweenYears(sub.getMinYear(), sub.getMaxYear()))
                .and(specs.betweenPrices(sub.getMinPrice(), sub.getMaxPrice()))
                .and(specs.betweenMileages(sub.getMinMileage(), sub.getMaxMileage()))
                .and(specs.equalLoanOption(sub.getLoanOption()))
                .and(specs.equalBarterOption(sub.getBarterOption()))
                .and(specs.equalCashOption(sub.getCashOption()))
                .and(specs.equalLeaseOption(sub.getLeaseOption()));

        return listingRepo.findAll(spec);
    }

    @Override
    public Page<Listing> searchListingsByPage(SearchDTO params) {
        Pageable pageable = PageRequest.of(0, 10);

        Specification<Listing> spec = Specification
                .where(specs.equalMake(params.getMake()))
                .and(specs.equalModel(params.getModel()))
                .and(specs.equalLocation(params.getLocation()))
                .and(specs.equalFuelType(params.getFuelType()))
                .and(specs.equalBodyType(params.getBodyType()))
                .and(specs.betweenYears(params.getMinYear(), params.getMaxYear()))
                .and(specs.betweenPrices(params.getMinPrice(), params.getMaxPrice()))
                .and(specs.betweenMileages(params.getMinMileage(), params.getMaxMileage()))
                .and(specs.equalLoanOption(params.getLoanOption()))
                .and(specs.equalBarterOption(params.getBarterOption()))
                .and(specs.equalCashOption(params.getCashOption()))
                .and(specs.equalLeaseOption(params.getLeaseOption()));

        return listingRepo.findAll(spec, pageable);
    }
}
