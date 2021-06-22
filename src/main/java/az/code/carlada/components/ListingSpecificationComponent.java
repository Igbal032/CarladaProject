package az.code.carlada.components;

import az.code.carlada.enums.BodyType;
import az.code.carlada.enums.FuelType;
import az.code.carlada.models.Listing;
import org.springframework.data.jpa.domain.Specification;

public interface ListingSpecificationComponent {
    Specification<Listing> equalMake(Long id);

    Specification<Listing> equalModel(Long id);

    Specification<Listing> equalLocation(Long id);

    Specification<Listing> equalFuelType(FuelType fuelType);

    Specification<Listing> equalBodyType(BodyType bodyType);

    Specification<Listing> betweenYears(Integer minYear, Integer maxYear);

    Specification<Listing> betweenPrices(Integer minPrice, Integer maxPrice);

    Specification<Listing> betweenMileages(Integer minMileage, Integer maxMileage);

    Specification<Listing> equalLoanOption(Boolean loanOption);

    Specification<Listing> equalCashOption(Boolean cashOption);

    Specification<Listing> equalBarterOption(Boolean barterOption);

    Specification<Listing> equalLeaseOption(Boolean leaseOption);
}
