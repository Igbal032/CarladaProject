package az.code.carlada.services;

import az.code.carlada.daos.UserDAO;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public Double addAmount(String email,Double amount) {
        Double userAmount = userDAO.addAmount(email,amount);
        return userAmount;
    }
}
