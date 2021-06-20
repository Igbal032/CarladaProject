package az.code.carlada.daos;

import az.code.carlada.dtos.SubscriptionDTO;
import az.code.carlada.exceptions.CityNotFound;
import az.code.carlada.exceptions.ModelNotFound;
import az.code.carlada.exceptions.SubscriptionNotFound;
import az.code.carlada.models.AppUser;
import az.code.carlada.models.Subscription;
import az.code.carlada.repositories.CityRepo;
import az.code.carlada.repositories.ModelRepo;
import az.code.carlada.repositories.SpecificationRepo;
import az.code.carlada.repositories.SubscriptionRepo;
import az.code.carlada.utils.ModelMapperUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SubscriptionDAOImpl implements SubscriptionDAO {
    SubscriptionRepo subRepo;
    ModelMapperUtil mapperUtil;
    CityRepo cityRepo;
    ModelRepo modelRepo;
    SpecificationRepo specRepo;

    public SubscriptionDAOImpl(SubscriptionRepo subRepo,
                               CityRepo cityRepo,
                               ModelRepo modelRepo,
                               SpecificationRepo specRepo,
                               ModelMapper mapper) {
        this.cityRepo = cityRepo;
        this.modelRepo = modelRepo;
        this.specRepo = specRepo;
        this.subRepo = subRepo;
        this.mapperUtil = ModelMapperUtil.builder().modelMapper(mapper).build();
    }

    @Override
    public Subscription saveSubscription(SubscriptionDTO s, AppUser appUser) {
        if (s.getSubId() != null) checkSubscription(s.getSubId(), appUser);

        if (cityRepo.findById(s.getCityId()).isEmpty())
            throw new CityNotFound("City couldn't found for given id");

        if (modelRepo.findById(s.getModelId()).isEmpty())
            throw new ModelNotFound("Model couldn't found for given id");

        Subscription subscription = mapperUtil.convertDTOToSubscription(s);

        return subRepo.save(subscription.toBuilder()
                .appUser(appUser)
                .specs(specRepo.findAllById(s.getSpecs()))
                .city(cityRepo.findById(s.getCityId()).get())
                .model(modelRepo.findById(s.getModelId()).get())
                .build());
    }

    @Override
    public List<Subscription> getSubscriptions(AppUser appUser) {
        return subRepo.findAllByAppUser(appUser);
    }

    @Override
    public Subscription getSubscription(Long id, AppUser appUser) {
        if (subRepo.findSubscriptionBySubIdAndAppUser(id, appUser).isEmpty())
            throw new SubscriptionNotFound("Subscription for given id couldn't found");

        return subRepo.findSubscriptionBySubIdAndAppUser(id, appUser).get();
    }

    @Override
    public Boolean checkSubscription(Long id, AppUser appUser) {
        if (!subRepo.existsSubscriptionBySubIdAndAppUser(id, appUser))
            throw new SubscriptionNotFound("Subscription for given id couldn't found");

        return true;
    }

    @Override
    public void disableSubscription(Long id, AppUser appUser) {
        checkSubscription(id, appUser);
        subRepo.deleteById(id);
    }
}
