package az.code.carlada.repositories;


import az.code.carlada.enums.Status;
import az.code.carlada.models.Listing;
import org.hibernate.sql.Select;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


import javax.transaction.Transactional;

public interface ListingRepo extends JpaRepository<Listing, Long>, JpaSpecificationExecutor<Listing> {
    Page<Listing> getAllByTypeEquals(Status type, Pageable pageable);
    Page<Listing> getAllByAppUserUsername(String appUser_username, Pageable pageable);
    Listing getListingByAppUserUsernameAndId(String username, Long id);

    @Transactional
    @Modifying
    @Query("update Listing  set isActive=false where expiredAt<= current_date and autoPay=false and isActive=true ")
    void disableExpired();
    @Query("from Listing where expiredAt<= current_date and autoPay=false and isActive=true ")
    List<Listing> getWaitingExpired();
}

