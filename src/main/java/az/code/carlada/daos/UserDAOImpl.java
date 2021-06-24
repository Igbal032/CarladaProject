package az.code.carlada.daos;

import az.code.carlada.daos.interfaces.UserDAO;
import az.code.carlada.exceptions.UserNotFound;
import az.code.carlada.models.AppUser;
import az.code.carlada.repositories.UserRepo;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserDAOImpl implements UserDAO {

    UserRepo userRepo;

    public UserDAOImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public AppUser getUserByUsername(String username) {
        Optional<AppUser> appUser = userRepo.getAppUserByUsername(username);
        if (appUser.isEmpty())
            throw new UserNotFound("User Not Found");

        return appUser.get();
    }


    public AppUser saveUser(AppUser user){
        return userRepo.save(user);
    }
}
