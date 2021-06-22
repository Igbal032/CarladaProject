package az.code.carlada.daos;

import az.code.carlada.dtos.*;
import az.code.carlada.exceptions.DataNotFound;
import az.code.carlada.exceptions.SubscriptionNotFound;
import az.code.carlada.models.AppUser;
import az.code.carlada.models.Subscription;
import az.code.carlada.repositories.*;
import az.code.carlada.components.ModelMapperComponent;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class SubscriptionDAOImpl implements SubscriptionDAO {
    SubscriptionRepo subRepo;
    ModelMapperComponent mapperService;

    public SubscriptionDAOImpl(SubscriptionRepo subRepo, ModelMapperComponent mapperService) {
        this.subRepo = subRepo;
        this.mapperService = mapperService;
    }

    @Override
    public Subscription saveSubscription(SubscriptionDTO s, AppUser appUser) {
        Subscription sub = mapperService.convertDTOToSubscription(s);
        if (s.getSubId() != null) {
            sub.setCreationDate(getSubscription(s.getSubId(), appUser).getCreationDate());
            return subRepo.save(sub);
        }
        return subRepo.save(sub.toBuilder()
                .appUser(appUser)
                .creationDate(LocalDateTime.now())
                .build());
    }

    @Override
    public List<Subscription> getSubscriptions(AppUser appUser) {
        return subRepo.findAllByAppUser(appUser);
    }

    @Override
    public List<Subscription> getAllSubscriptions() {
        return subRepo.findAll();
    }

    @Override
    public Subscription getSubscription(Long id, AppUser appUser) {
        Optional<Subscription> subscription = subRepo.findSubscriptionBySubIdAndAppUser(id, appUser);
        if (subscription.isEmpty())
            throw new SubscriptionNotFound("Subscription for given id couldn't found");

        return subscription.get();
    }

    @Override
    public void deleteSubscription(Long id, AppUser appUser) {
        subRepo.delete(getSubscription(id, appUser));
    }
}
