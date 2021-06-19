package az.code.carlada.services;

import az.code.carlada.enums.BodyType;
import az.code.carlada.enums.FuelType;
import az.code.carlada.models.Listing;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class SearchSpecificationServiceImpl implements SearchSpecificationService {
    @Override
    public Specification<Listing> equalMake(Integer id) {
        if (id == null) return null;
        return (root, query, cb) -> cb.equal(root.get("car").get("model").get("make").get("id"), id);
    }

    @Override
    public Specification<Listing> equalModel(Integer id) {
        if (id == null) return null;
        return (root, query, cb) -> cb.equal(root.get("car").get("model").get("id"), id);
    }

    @Override
    public Specification<Listing> equalLocation(Integer id) {
        if (id == null) return null;
        return (root, query, cb) -> cb.equal(root.get("city").get("id"), id);
    }

    @Override
    public Specification<Listing> equalFuelType(FuelType fuelType) {
        if (fuelType == null) return null;
        return (root, query, cb) -> cb.equal(root.get("car").get("carDetail").get("fuelType"), fuelType);
    }

    @Override
    public Specification<Listing> equalBodyType(BodyType bodyType) {
        if (bodyType == null) return null;
        return (root, query, cb) -> cb.equal(root.get("car").get("carDetail").get("bodyType"), bodyType);
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
        if (loanOption == null) return null;
        return (root, query, cb) -> cb.equal(root.get("car").get("loanOption"), loanOption);
    }

    @Override
    public Specification<Listing> equalCashOption(Boolean cashOption) {
        if (cashOption == null) return null;
        return (root, query, cb) -> cb.equal(root.get("car").get("cashOption"), cashOption);
    }

    @Override
    public Specification<Listing> equalBarterOption(Boolean barterOption) {
        if (barterOption == null) return null;
        return (root, query, cb) -> cb.equal(root.get("car").get("barterOption"), barterOption);
    }

    public Specification<Listing> equalLeaseOption(Boolean leaseOption) {
        if (leaseOption == null) return null;
        return (root, query, cb) -> cb.equal(root.get("car").get("leaseOption"), leaseOption);
    }
}
