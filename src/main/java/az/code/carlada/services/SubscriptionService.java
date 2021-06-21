package az.code.carlada.services;

import az.code.carlada.dtos.SubscriptionDTO;
import az.code.carlada.dtos.SubscriptionListDTO;

import java.util.List;

public interface SubscriptionService {
    SubscriptionListDTO saveSubscription(SubscriptionDTO subDTO);

    List<SubscriptionListDTO> getSubscriptions();

    SubscriptionListDTO getSubscriptionById(Long id);

    void disableSubscription(Long id);
}
