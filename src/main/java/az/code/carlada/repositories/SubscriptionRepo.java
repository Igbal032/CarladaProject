package az.code.carlada.repositories;

import az.code.carlada.models.AppUser;
import az.code.carlada.models.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepo extends JpaRepository<Subscription, Long> {
    List<Subscription> findAllByAppUser(AppUser appUser);

    Optional<Subscription> findSubscriptionBySubIdAndAppUser(Long subId, AppUser appUser);

    Boolean existsSubscriptionBySubIdAndAppUser(Long subId, AppUser appUser);
}
