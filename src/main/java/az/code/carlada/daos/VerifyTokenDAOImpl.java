package az.code.carlada.daos;

import az.code.carlada.daos.interfaces.VerifyTokenDAO;
import az.code.carlada.exceptions.DataNotFound;
import az.code.carlada.models.VerificationToken;
import az.code.carlada.repositories.VerificationTokenRepo;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class VerifyTokenDAOImpl implements VerifyTokenDAO {
    VerificationTokenRepo vtRepo;

    public VerifyTokenDAOImpl(VerificationTokenRepo vtRepo) {
        this.vtRepo = vtRepo;
    }

    @Override
    public VerificationToken save(VerificationToken verifyToken) {
        return vtRepo.save(verifyToken);
    }

    @Override
    public VerificationToken findByToken(String token) {
        Optional<VerificationToken> vToken = vtRepo.findByToken(token);
        if (vToken.isEmpty()) throw new DataNotFound("Token couldn't found");
        return vToken.get();
    }

    @Override
    public void delete(VerificationToken verifyToken) {
        if (verifyToken != null)
            vtRepo.delete(verifyToken);
    }
}
