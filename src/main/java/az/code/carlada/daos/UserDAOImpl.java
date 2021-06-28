package az.code.carlada.daos;

import az.code.carlada.daos.interfaces.UserDAO;
import az.code.carlada.dtos.UserDTO;
import az.code.carlada.exceptions.UserNotFound;
import az.code.carlada.models.AppUser;
import az.code.carlada.repositories.UserRepo;
import az.code.carlada.utils.BasicUtil;
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

    @Override
    public AppUser getUserByEmail(String email) {
        Optional<AppUser> appUser = userRepo.getAppUserByEmail(email);
        if (appUser.isEmpty())
            throw new UserNotFound("User Not Found");
        return appUser.get();
    }

    @Override
    public AppUser createUser(UserDTO userDTO) {
        String slag = BasicUtil.createSlug(userDTO.getEmail());
        AppUser newUser = AppUser.builder()
                .username(slag)
                .fullName(userDTO.getFirstname()+" "+userDTO.getLastname())
                .email(userDTO.getEmail())
                .phone(userDTO.getPhoneNumber())
                .amount(0.0)
                .build();
        return userRepo.save(newUser);
    }


    public AppUser saveUser(AppUser user){
        return userRepo.save(user);
    }
}
