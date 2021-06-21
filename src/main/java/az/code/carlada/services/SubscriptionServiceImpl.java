package az.code.carlada.services;

import az.code.carlada.daos.SubscriptionDAO;
import az.code.carlada.daos.UserDAO;
import az.code.carlada.dtos.SubscriptionDTO;
import az.code.carlada.dtos.SubscriptionListDTO;
import az.code.carlada.models.AppUser;
import az.code.carlada.utils.ModelMapperUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    SubscriptionDAO subDAO;
    AppUser appUser;
    ModelMapperUtil mapperUtil;
    UserDAO userDAO;

    public SubscriptionServiceImpl(SubscriptionDAO subDAO, ModelMapper modelMapper, UserDAO userDAO) {
        this.subDAO = subDAO;
        this.mapperUtil = ModelMapperUtil.builder().modelMapper(modelMapper).build();
        this.userDAO = userDAO;
    }

    @PostConstruct
    public void init() {
        String username = "shafig";
        this.appUser = userDAO.getUserByUsername(username);
    }

    @Override
    public SubscriptionListDTO saveSubscription(SubscriptionDTO subDTO) {
        return mapperUtil.convertSubscriptionToListDTO(subDAO.saveSubscription(subDTO, appUser));
    }

    @Override
    public List<SubscriptionListDTO> getSubscriptions() {
        return subDAO.getSubscriptions(appUser)
                .stream()
                .map(i -> mapperUtil.convertSubscriptionToListDTO(i))
                .collect(Collectors.toList());
    }

    @Override
    public SubscriptionListDTO getSubscriptionById(Long id) {
        return mapperUtil.convertSubscriptionToListDTO(subDAO.getSubscription(id, appUser));
    }

    @Override
    public void disableSubscription(Long id) {
        subDAO.disableSubscription(id, appUser);
    }
}
