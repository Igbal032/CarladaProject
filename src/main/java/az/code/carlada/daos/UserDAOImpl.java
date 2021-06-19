package az.code.carlada.daos;

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
    public Double addAmount(String email,Double amount) {
        AppUser appUser = userRepo.getAppUserByEmail(email);
        if (appUser!=null){
            if (amount<1){
                return appUser.getAmount();
            }
            Double totalAmount = appUser.getAmount()+amount;
            appUser.setAmount(totalAmount);
            userRepo.save(appUser);
           return totalAmount;
        }
        throw new UserNotFound("User Not Found");
    }
}
