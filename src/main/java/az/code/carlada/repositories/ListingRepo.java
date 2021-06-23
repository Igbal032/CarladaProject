package az.code.carlada.repositories;


import az.code.carlada.models.AppUser;
import az.code.carlada.models.Listing;
import az.code.carlada.models.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


import javax.transaction.Transactional;
import java.util.List;

public interface ListingRepo extends JpaRepository<Listing, Long>, JpaSpecificationExecutor<Listing> {
    Page<Listing> getAllByStatusType(Status statusType, Pageable pageable);
    Page<Listing> getAllByAppUserUsername(String appUser_username, Pageable pageable);
    Listing getListingByAppUserUsernameAndId(String username, Long id);

    @Transactional
    @Modifying
    @Query("update Listing  set isActive=false where expiredAt<= current_date and autoPay=false and isActive=true ")
    void disableExpired();
    @Query("from Listing where expiredAt <= current_date and autoPay=false and isActive=true ")
    List<Listing> getWaitingExpired();
}

