package az.code.carlada.daos.interfaces;

import az.code.carlada.models.VerificationToken;

public interface VerifyTokenDAO {
    VerificationToken findByToken(String email);

    void delete(VerificationToken token);

    VerificationToken save(VerificationToken token);
}
