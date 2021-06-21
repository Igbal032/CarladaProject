package az.code.carlada.repositories;

import az.code.carlada.enums.Status;
import az.code.carlada.models.Listing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ListingRepo extends JpaRepository<Listing, Long>, JpaSpecificationExecutor<Listing> {
    List<Listing> getAllByTypeEquals(Status type);
    List<Listing> getAllByAppUserIsNotNull();
    List<Listing> getAllByAppUserUsername(String slug);
    //List<Listing> getAllByAppUserEmail(String email);
    //List<Listing> getAllByAppUserUsername(String username);
    //Listing getAllByAppUserEmailAndId(String email,Long id);
    Listing getListingByAppUserUsernameAndId(String username, Long id);
}