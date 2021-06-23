package az.code.carlada.repositories;


import az.code.carlada.enums.Status;
import az.code.carlada.models.Listing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


import javax.transaction.Transactional;
import java.util.List;

public interface ListingRepo extends JpaRepository<Listing, Long>, JpaSpecificationExecutor<Listing> {
    Page<Listing> getAllByTypeEquals(Status type, Pageable pageable);

    Page<Listing> getAllByAppUserUsername(String appUser_username, Pageable pageable);

    //List<Listing> getAllByAppUserEmail(String email);
    //List<Listing> getAllByAppUserUsername(String username);
    //Listing getAllByAppUserEmailAndId(String email,Long id);
    Listing getListingByAppUserUsernameAndId(String username, Long id);

    @Transactional
    @Modifying
    @Query("update Listing  set isActive=false where expiredAt<= current_date")
    void disableExpired();
}

