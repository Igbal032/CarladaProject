package az.code.carlada.daos;

import az.code.carlada.dtos.*;
import az.code.carlada.exceptions.DataNotFound;
import az.code.carlada.exceptions.SubscriptionNotFound;
import az.code.carlada.models.AppUser;
import az.code.carlada.models.Subscription;
import az.code.carlada.repositories.*;
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
    MakeRepo makeRepo;
    SpecificationRepo specRepo;

    public SubscriptionDAOImpl(SubscriptionRepo subRepo,
                               CityRepo cityRepo,
                               ModelRepo modelRepo,
                               SpecificationRepo specRepo,
                               MakeRepo makeRepo,
                               ModelMapper mapper) {
        this.cityRepo = cityRepo;
        this.makeRepo = makeRepo;
        this.modelRepo = modelRepo;
        this.specRepo = specRepo;
        this.subRepo = subRepo;
        this.mapperUtil = ModelMapperUtil.builder().modelMapper(mapper).build();
    }

    @Override
    public Subscription saveSubscription(SubscriptionDTO s, AppUser appUser) {
        if (s.getSubId() != null) checkSubscription(s.getSubId(), appUser);

        Subscription sub = mapperUtil.convertDTOToSubscription(s);

        if (s.getCityId() != null){
            if(cityRepo.findById(s.getCityId()).isEmpty())
                throw new DataNotFound("City couldn't found for given id");

            sub.setCity(cityRepo.findById(s.getCityId()).get());
        }

        if (s.getMakeId() != null) {
            if(makeRepo.findById(s.getMakeId()).isEmpty())
            throw new DataNotFound("Make couldn't found for given id");
        }

        if (s.getModelId() != null){
            if(modelRepo.findById(s.getModelId()).isEmpty())
                throw new DataNotFound("Model couldn't found for given id");

            sub.setModel(modelRepo.findById(s.getModelId()).get());
        }

        if (s.getSpecs() != null) {
            s.getSpecs().forEach(i -> {
                if (specRepo.findById(i).isEmpty())
                    throw new DataNotFound("Specifications couldn't found for given id");
            });
            sub.setSpecs(specRepo.findAllById(s.getSpecs()));
        }

        return subRepo.save(sub.toBuilder()
                .appUser(appUser)
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
