package az.code.carlada.daos;

import az.code.carlada.exceptions.IllegalSignException;
import az.code.carlada.exceptions.UserNotFound;
import az.code.carlada.models.AppUser;
import az.code.carlada.repositories.UserRepo;
import org.springframework.stereotype.Component;

@Component
public class UserDAOImpl implements UserDAO {

    UserRepo userRepo;

    public UserDAOImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public Double addAmount(String username,Double amount) {
        AppUser appUser = userRepo.getAppUserByUsername(username);
        if (appUser!=null){
            if (amount<1){
                throw new IllegalSignException("Amount must not be less than 1");
            }
            Double totalAmount = appUser.getAmount()+amount;
            appUser.setAmount(totalAmount);
            userRepo.save(appUser);
           return totalAmount;
        }
        throw new UserNotFound("User Not Found");
    }
}
