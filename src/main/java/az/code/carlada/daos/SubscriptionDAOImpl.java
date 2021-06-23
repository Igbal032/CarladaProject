package az.code.carlada.daos;

import az.code.carlada.dtos.*;
import az.code.carlada.exceptions.SubscriptionNotFound;
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
    UserDAO userDAO;
    ModelMapperComponent mapperService;

    public SubscriptionDAOImpl(SubscriptionRepo subRepo, UserDAO userDAO, ModelMapperComponent mapperService) {
        this.subRepo = subRepo;
        this.userDAO = userDAO;
        this.mapperService = mapperService;
    }

    @Override
    public Subscription saveSubscription(SubscriptionDTO s, String username) {
        Subscription sub = mapperService.convertDTOToSubscription(s);
        if (s.getSubId() != null) {
            sub.setCreationDate(getSubscription(s.getSubId(), username).getCreationDate());
            return subRepo.save(sub);
        }
        return subRepo.save(sub.toBuilder()
                .appUser(userDAO.getUserByUsername(username))
                .creationDate(LocalDateTime.now())
                .build());
    }

    @Override
    public List<Subscription> getSubscriptions(String username) {
        return subRepo.findAllByAppUser(userDAO.getUserByUsername(username));
    }

    @Override
    public Subscription getSubscription(Long id, String username) {
        Optional<Subscription> subscription = subRepo
                .findSubscriptionBySubIdAndAppUser(id, userDAO.getUserByUsername(username));
        if (subscription.isEmpty())
            throw new SubscriptionNotFound("Subscription for given id couldn't found");

        return subscription.get();
    }

    @Override
    public void deleteSubscription(Long id, String username) {
        subRepo.delete(getSubscription(id, username));
    }
}
