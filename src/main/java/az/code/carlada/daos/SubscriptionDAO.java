package az.code.carlada.daos;

import az.code.carlada.dtos.SubscriptionDTO;
import az.code.carlada.models.Subscription;

import java.util.List;

public interface SubscriptionDAO {
    Subscription saveSubscription(SubscriptionDTO sub, String username);

    List<Subscription> getSubscriptions(String username);


    Subscription getSubscription(Long id, String username);

    void deleteSubscription(Long id, String username);
}
