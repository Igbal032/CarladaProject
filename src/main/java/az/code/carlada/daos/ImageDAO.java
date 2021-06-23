package az.code.carlada.daos;

import az.code.carlada.models.Image;

import java.util.List;
import java.util.Optional;

public interface ImageDAO {
    Image addImgToListing(Long id, String link);
    Image getImgFromListingById(Long listingId, Long imgId);
    List<Image> getAllImgFromListing(Long listingId);
    void deleteImgFromListing(Long listingId, Long imgId);
    Image findImageById(Long id);
}
