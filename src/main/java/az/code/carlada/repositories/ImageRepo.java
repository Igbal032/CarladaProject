package az.code.carlada.repositories;

import az.code.carlada.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepo extends JpaRepository<Image, Long> {
    List<Image> getAllByListing_Id(Long id);
    Image getImageByListing_IdAndAndId(Long listingId, Long imgId);
}
