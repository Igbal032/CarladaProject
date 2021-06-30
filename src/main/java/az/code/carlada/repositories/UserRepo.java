package az.code.carlada.repositories;

import az.code.carlada.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<AppUser, Long> {
    Optional<AppUser> getAppUserByEmail(String email);

    Optional<AppUser> getAppUserByUsername(String username);

}
