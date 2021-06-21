package az.code.carlada.repositories;

import az.code.carlada.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepo extends JpaRepository<AppUser,Double> {
    AppUser getAppUserByEmail(String email);
    AppUser getAppUserByUsername(String username);
}
