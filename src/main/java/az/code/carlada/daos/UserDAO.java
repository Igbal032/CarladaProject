package az.code.carlada.daos;

import az.code.carlada.models.AppUser;

public interface UserDAO {
    Double addAmount(String usernames,Double amount);

    AppUser getUserByUsername(String username);
}
