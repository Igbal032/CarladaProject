package az.code.carlada.daos.interfaces;

import az.code.carlada.dtos.UserDTO;
import az.code.carlada.enums.TransactionType;
import az.code.carlada.models.AppUser;
import az.code.carlada.models.Status;
import az.code.carlada.models.Transaction;

public interface UserDAO {
    AppUser saveUser(AppUser appUser);
    AppUser getUserByUsername(String username);
    AppUser getUserByEmail(String email);
    AppUser createUser(UserDTO userDTO);
}
