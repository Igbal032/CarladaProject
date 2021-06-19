package az.code.carlada.repositories;

import az.code.carlada.models.Listing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ListingRepo extends JpaRepository<Listing,Long>, JpaSpecificationExecutor<Listing> {
}
