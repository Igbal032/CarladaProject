package az.code.carlada.repositories;

import az.code.carlada.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<AppUser, Long> {
    AppUser getAppUserByUsername(String username);
}