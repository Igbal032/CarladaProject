package az.code.carlada.repositories;

import az.code.carlada.models.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerificationTokenRepo extends JpaRepository<VerificationToken, String> {
    Optional<VerificationToken> findByToken(String token);
}
