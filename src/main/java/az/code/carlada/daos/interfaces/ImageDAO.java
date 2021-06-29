package az.code.carlada.daos.interfaces;

import az.code.carlada.models.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ImageDAO {
    Image addImgToListing(Image image, String username);

    Image getImgFromListingById(Long listingId, Long imgId);

    List<Image> getAllImgFromListing(Long listingId);

    void deleteImgFromListing(Long listingId, Long imgId, String username);

    Image findImageById(Long id);

}
