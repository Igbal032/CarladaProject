package az.code.carlada.components;

import az.code.carlada.enums.BodyType;
import az.code.carlada.enums.FuelType;
import az.code.carlada.models.Listing;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ListingSpecificationComponentImpl implements ListingSpecificationComponent {
    @Override
    public Specification<Listing> equalMake(Long id) {
        return (root, query, cb) -> Objects.nonNull(id) ?
                cb.equal(root.get("car").get("model").get("make").get("id"), id) :
                cb.conjunction();
    }

    @Override
    public Specification<Listing> equalModel(Long id) {
        return (root, query, cb) -> Objects.nonNull(id) ?
                cb.equal(root.get("car").get("model").get("id"), id) :
                cb.conjunction();
    }

    @Override
    public Specification<Listing> equalLocation(Long id) {
        return (root, query, cb) -> Objects.nonNull(id) ?
                cb.equal(root.get("city").get("id"), id) :
                cb.conjunction();
    }

    @Override
    public Specification<Listing> equalFuelType(FuelType fuelType) {
        return (root, query, cb) -> Objects.nonNull(fuelType) ?
                cb.equal(root.get("car").get("carDetail").get("fuelType"), fuelType) :
                cb.conjunction();
    }

    @Override
    public Specification<Listing> equalBodyType(BodyType bodyType) {
        return (root, query, cb) -> Objects.nonNull(bodyType) ?
                cb.equal(root.get("car").get("carDetail").get("bodyType"), bodyType) :
                cb.conjunction();
    }

    @Override
    public Specification<Listing> betweenYears(Integer minYear, Integer maxYear) {
        if (minYear == null && maxYear == null) return null;
        if (minYear == null) return (root, query, cb) -> cb.lessThan(root.get("car").get("year"), maxYear);
        if (maxYear == null) return (root, query, cb) -> cb.greaterThan(root.get("car").get("year"), minYear);
        return (root, query, cb) -> cb.between(root.get("car").get("year"), minYear, maxYear);
    }

    @Override
    public Specification<Listing> betweenPrices(Integer minPrice, Integer maxPrice) {
        if (minPrice == null && maxPrice == null) return null;
        if (minPrice == null) return (root, query, cb) -> cb.lessThan(root.get("car").get("price"), maxPrice);
        if (maxPrice == null) return (root, query, cb) -> cb.greaterThan(root.get("car").get("price"), minPrice);
        return (root, query, cb) -> cb.between(root.get("car").get("price"), minPrice, maxPrice);
    }

    @Override
    public Specification<Listing> betweenMileages(Integer minMileage, Integer maxMileage) {
        if (minMileage == null && maxMileage == null) return null;
        if (minMileage == null) return (root, query, cb) -> cb.lessThan(root.get("car").get("mileage"), maxMileage);
        if (maxMileage == null) return (root, query, cb) -> cb.greaterThan(root.get("car").get("mileage"), minMileage);
        return (root, query, cb) -> cb.between(root.get("car").get("mileage"), minMileage, maxMileage);
    }

    @Override
    public Specification<Listing> equalLoanOption(Boolean loanOption) {
        return (root, query, cb) -> Objects.nonNull(loanOption) ?
                cb.equal(root.get("car").get("loanOption"), loanOption) :
                cb.conjunction();
    }

    @Override
    public Specification<Listing> equalCashOption(Boolean cashOption) {
        return (root, query, cb) -> Objects.nonNull(cashOption) ?
                cb.equal(root.get("car").get("cashOption"), cashOption) :
                cb.conjunction();
    }

    @Override
    public Specification<Listing> equalBarterOption(Boolean barterOption) {
        return (root, query, cb) -> Objects.nonNull(barterOption) ?
                cb.equal(root.get("car").get("barterOption"), barterOption) :
                cb.conjunction();
    }

    public Specification<Listing> equalLeaseOption(Boolean leaseOption) {
        return (root, query, cb) -> Objects.nonNull(leaseOption) ?
                cb.equal(root.get("car").get("leaseOption"), leaseOption) :
                cb.conjunction();
    }
}
