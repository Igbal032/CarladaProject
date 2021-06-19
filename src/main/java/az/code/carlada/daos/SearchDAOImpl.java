package az.code.carlada.daos;

import az.code.carlada.dtos.SearchDTO;
import az.code.carlada.models.Listing;
import az.code.carlada.repositories.ListingRepo;
import az.code.carlada.services.SearchSpecificationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SearchDAOImpl implements SearchDAO {
    ListingRepo listingRepo;
    SearchSpecificationService specs;

    public SearchDAOImpl(ListingRepo listingRepo, SearchSpecificationService specs) {
        this.listingRepo = listingRepo;
        this.specs = specs;
    }

    @Override
    public List<Listing> searchListings(SearchDTO params) {
        return listingRepo.findAll();
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
