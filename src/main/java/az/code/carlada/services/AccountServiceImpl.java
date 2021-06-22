package az.code.carlada.services;

import az.code.carlada.dtos.UserDTO;
import az.code.carlada.models.AppUser;
import az.code.carlada.repositories.UserRepo;
import az.code.carlada.utils.BasicUtil;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService{
    UserRepo userRepo;

    public AccountServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    /*
create newUser for database
 */
    @Override
    public UserDTO createUser(UserDTO userDTO) {
        String slag = userDTO.getEmail()
                .replace('@','-')
                .replace('.','-');
        AppUser newUser = AppUser.builder()
                .username(slag)
                .fullName(userDTO.getFirstname()+" "+userDTO.getLastname())
                .email(userDTO.getEmail())
//                .phone(userDTO)
                .build();
        userRepo.save(newUser);
        return userDTO;
    }

    @Override
    public UserRepresentation user(UserDTO userDTO) {
        UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
        user.setUsername(userDTO.getEmail());
        user.setFirstName(userDTO.getFirstname());
        user.setLastName(userDTO.getLastname());
        user.setEmail(userDTO.getEmail());
        return user;
    }
}