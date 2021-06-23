package az.code.carlada.components;

import az.code.carlada.enums.BodyType;
import az.code.carlada.enums.FuelType;
import az.code.carlada.models.Subscription;
import org.springframework.data.jpa.domain.Specification;

public interface SubscriptionSpecificationComponent {
    Specification<Subscription> equalMake(Long id);

    Specification<Subscription> equalModel(Long id);

    Specification<Subscription> equalLocation(Long id);

    Specification<Subscription> equalFuelType(FuelType fuelType);

    Specification<Subscription> equalBodyType(BodyType bodyType);

    Specification<Subscription> betweenIntegers(Integer number, String minNumber, String maxNumber);

    Specification<Subscription> equalLoanOption(Boolean loanOption);

    Specification<Subscription> equalCashOption(Boolean cashOption);

    Specification<Subscription> equalBarterOption(Boolean barterOption);

    Specification<Subscription> equalLeaseOption(Boolean leaseOption);
}
