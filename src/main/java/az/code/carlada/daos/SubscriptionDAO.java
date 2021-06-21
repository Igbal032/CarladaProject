package az.code.carlada.daos;

import az.code.carlada.dtos.SubscriptionDTO;
import az.code.carlada.models.AppUser;
import az.code.carlada.models.Subscription;

import java.util.List;

public interface SubscriptionDAO {
    Subscription saveSubscription(SubscriptionDTO sub, AppUser appUser);

    List<Subscription> getSubscriptions(AppUser appUser);

    Subscription getSubscription(Long id, AppUser appUser);

    Boolean checkSubscription(Long id, AppUser appUser);

    void disableSubscription(Long id, AppUser appUser);
}
