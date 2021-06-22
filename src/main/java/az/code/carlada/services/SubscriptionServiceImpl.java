package az.code.carlada.services;

import az.code.carlada.components.ModelMapperComponent;
import az.code.carlada.daos.SubscriptionDAO;
import az.code.carlada.daos.UserDAO;
import az.code.carlada.dtos.SubscriptionDTO;
import az.code.carlada.dtos.SubscriptionListDTO;
import az.code.carlada.models.AppUser;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    SubscriptionDAO subDAO;
    AppUser appUser;
    ModelMapperComponent mapperService;
    UserDAO userDAO;

    public SubscriptionServiceImpl(SubscriptionDAO subDAO, ModelMapperComponent mapperService, UserDAO userDAO) {
        this.subDAO = subDAO;
        this.mapperService = mapperService;
        this.userDAO = userDAO;
    }


    @Override
    public SubscriptionListDTO saveSubscription(SubscriptionDTO subDTO) {
        return mapperService.convertSubscriptionToListDTO(subDAO.saveSubscription(subDTO, appUser));
    }

    @Override
    public List<SubscriptionListDTO> getSubscriptions() {
        return subDAO.getSubscriptions(appUser)
                .stream()
                .map(i -> mapperService.convertSubscriptionToListDTO(i))
                .collect(Collectors.toList());
    }

    @Override
    public SubscriptionListDTO getSubscriptionById(Long id) {
        return mapperService.convertSubscriptionToListDTO(subDAO.getSubscription(id, appUser));
    }

    @Override
    public void deleteSubscription(Long id) {
        subDAO.deleteSubscription(id, appUser);
    }
}
