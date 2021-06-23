package az.code.carlada.services;

import az.code.carlada.dtos.SubscriptionDTO;
import az.code.carlada.dtos.SubscriptionListDTO;

import java.util.List;

public interface SubscriptionService {
    SubscriptionListDTO saveSubscription(SubscriptionDTO subDTO, String username);

    List<SubscriptionListDTO> getSubscriptions(String username);

    SubscriptionListDTO getSubscriptionById(Long id, String username);

    void deleteSubscription(Long id, String username);
}
