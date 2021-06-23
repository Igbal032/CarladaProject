package az.code.carlada.services;

import az.code.carlada.components.ModelMapperComponent;
import az.code.carlada.daos.SubscriptionDAO;
import az.code.carlada.daos.UserDAO;
import az.code.carlada.dtos.SubscriptionDTO;
import az.code.carlada.dtos.SubscriptionListDTO;
import az.code.carlada.models.AppUser;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    SubscriptionDAO subDAO;
    ModelMapperComponent mapperService;
    UserDAO userDAO;

    public SubscriptionServiceImpl(SubscriptionDAO subDAO, ModelMapperComponent mapperService, UserDAO userDAO) {
        this.subDAO = subDAO;
        this.mapperService = mapperService;
        this.userDAO = userDAO;
    }


    @Override
    public SubscriptionListDTO saveSubscription(SubscriptionDTO subDTO, String username) {
        return mapperService.convertSubscriptionToListDTO(subDAO.saveSubscription(subDTO, username));
    }

    @Override
    public List<SubscriptionListDTO> getSubscriptions(String username) {
        return subDAO.getSubscriptions(username)
                .stream()
                .map(i -> mapperService.convertSubscriptionToListDTO(i))
                .collect(Collectors.toList());
    }

    @Override
    public SubscriptionListDTO getSubscriptionById(Long id, String username) {
        return mapperService.convertSubscriptionToListDTO(subDAO.getSubscription(id, username));
    }

    @Override
    public void deleteSubscription(Long id, String username) {
        subDAO.deleteSubscription(id, username);
    }
}
