package az.code.carlada.daos;

import az.code.carlada.daos.interfaces.AccountDAO;
import az.code.carlada.dtos.UserDTO;
import az.code.carlada.models.AppUser;
import az.code.carlada.repositories.UserRepo;
import az.code.carlada.utils.BasicUtil;
import org.springframework.stereotype.Component;

@Component
public class AccountDAOImpl implements AccountDAO {
    UserRepo userRepo;

    public AccountDAOImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
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
}
