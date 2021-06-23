package az.code.carlada.components;

import az.code.carlada.enums.BodyType;
import az.code.carlada.enums.FuelType;
import az.code.carlada.models.Subscription;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Predicate;
import java.util.Objects;

@Component
public class SubscriptionSpecificationComponentImpl implements SubscriptionSpecificationComponent {

    @Override
    public Specification<Subscription> equalMake(Long id) {
        return (root, query, cb) -> Objects.nonNull(id) ?
                cb.or(cb.isNull(root.get("make").get("id")), cb.equal(root.get("make").get("id"), id)) :
                cb.conjunction();
    }

    @Override
    public Specification<Subscription> equalModel(Long id) {
        return (root, query, cb) -> Objects.nonNull(id) ?
                cb.or(cb.equal(root.get("model").get("id"), id), cb.isNull(root.get("model").get("id"))) :
                cb.conjunction();
    }

    @Override
    public Specification<Subscription> equalLocation(Long id) {
        return (root, query, cb) -> Objects.nonNull(id) ?
                cb.or(cb.equal(root.get("city").get("id"), id), cb.isNull(root.get("city").get("id"))) :
                cb.conjunction();
    }

    @Override
    public Specification<Subscription> equalFuelType(FuelType fuelType) {
        return (root, query, cb) -> Objects.nonNull(fuelType) ?
                cb.or(cb.equal(root.get("fuelType"), fuelType), cb.isNull(root.get("fuelType"))) :
                cb.conjunction();
    }

    @Override
    public Specification<Subscription> equalBodyType(BodyType bodyType) {
        return (root, query, cb) -> Objects.nonNull(bodyType) ?
                cb.or(cb.equal(root.get("bodyType"), bodyType), cb.isNull(root.get("bodyType"))) :
                cb.conjunction();
    }

    @Override
    public Specification<Subscription> betweenIntegers(Integer number, String minNumber, String maxNumber) {
        return (root, query, cb) -> {
            Predicate isBetween = cb.and(cb.lessThan(root.get(minNumber), number), cb.greaterThan(root.get(maxNumber), number));
            Predicate isLessThenMax = cb.and(cb.isNull(root.get(minNumber)), cb.greaterThan(root.get(maxNumber), number));
            Predicate isMoreThenMin = cb.and(cb.lessThan(root.get(minNumber), number), cb.isNull(root.get(maxNumber)));
            Predicate bothNull = cb.and(cb.isNull(root.get(minNumber)), cb.isNull(root.get(maxNumber)));

            return Objects.nonNull(number) ?
                    cb.or(isBetween, isLessThenMax, isMoreThenMin, bothNull) : cb.conjunction();
        };
    }


    @Override
    public Specification<Subscription> equalLoanOption(Boolean loanOption) {
        return (root, query, cb) -> Objects.nonNull(loanOption) ?
                cb.or(cb.equal(root.get("loanOption"), loanOption), cb.isNull(root.get("loanOption"))) :
                cb.conjunction();
    }

    @Override
    public Specification<Subscription> equalCashOption(Boolean cashOption) {
        return (root, query, cb) -> Objects.nonNull(cashOption) ?
                cb.or(cb.equal(root.get("cashOption"), cashOption), cb.isNull(root.get("cashOption"))) :
                cb.conjunction();
    }

    @Override
    public Specification<Subscription> equalBarterOption(Boolean barterOption) {
        return (root, query, cb) -> Objects.nonNull(barterOption) ?
                cb.or(cb.equal(root.get("barterOption"), barterOption), cb.isNull(root.get("barterOption"))) :
                cb.conjunction();
    }

    @Override
    public Specification<Subscription> equalLeaseOption(Boolean leaseOption) {
        return (root, query, cb) -> Objects.nonNull(leaseOption) ?
                cb.or(cb.equal(root.get("leaseOption"), leaseOption), cb.isNull(root.get("leaseOption"))) :
                cb.conjunction();
    }
}
