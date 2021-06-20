package az.code.carlada.daos;

import az.code.carlada.enums.Status;
import az.code.carlada.exceptions.EnoughBalanceException;
import az.code.carlada.exceptions.IllegalSignException;
import az.code.carlada.exceptions.ListingNotFound;
import az.code.carlada.exceptions.UserNotFound;
import az.code.carlada.models.AppUser;
import az.code.carlada.models.Listing;
import az.code.carlada.repositories.ListingRepository;
import az.code.carlada.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PaymentDAOImpl implements PaymentDAO{
    UserRepo userRepo;
    ListingRepository listingRepo;


    public PaymentDAOImpl(UserRepo userRepo,ListingRepository listingRepo) {
        this.listingRepo = listingRepo;
        this.userRepo = userRepo;
    }

    @Override
    public Listing payForListingStatus(Long listingId, Status statusType, String username) {
        Double vipStatus = Status.VIP.getStatusAmount();
        Double standardStatus = Status.STANDARD.getStatusAmount();
        Double defaultStatus = Status.DEFAULT.getStatusAmount();
        AppUser appUser = userRepo.getAppUserByUsername(username);
        Optional<Listing> listing;
        if (appUser!=null){
            listing = listingRepo.findById(listingId);
            if (listing.isPresent()){
                switch (statusType){
                    case VIP:
                        if (vipStatus<=appUser.getAmount()){
                            appUser.setAmount(appUser.getAmount()-vipStatus);
                        }
                        else {
                            throw new EnoughBalanceException("Balance is not enough");
                        }
                        break;
                    case DEFAULT:
                        if (defaultStatus<=appUser.getAmount()){
                            appUser.setAmount(appUser.getAmount()-defaultStatus);
                        }
                        else {
                            throw new EnoughBalanceException("Balance is not enough");
                        }
                        break;
                    case STANDARD:
                        if (standardStatus<=appUser.getAmount()){
                            appUser.setAmount(appUser.getAmount()-standardStatus);
                        }
                        else {
                            throw new EnoughBalanceException("Balance is not enough");
                        }
                        break;
                }
                listing.get().setType(statusType);
                listingRepo.save(listing.get());
                userRepo.save(appUser);
                return listing.get();
            }
            else {
                throw new ListingNotFound("Listing Not Found");
            }
        }
        throw new UserNotFound("User Not Found");
    }
}
