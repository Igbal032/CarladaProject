package az.code.carlada.daos;

import az.code.carlada.dtos.UserDTO;
import az.code.carlada.models.AppUser;

public interface AccountDAO {
    AppUser createUser(UserDTO userDTO);
}
