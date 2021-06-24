package az.code.carlada.services.interfaces;

import az.code.carlada.dtos.ImageDTO;
import az.code.carlada.dtos.UserDTO;
import az.code.carlada.models.AppUser;
import az.code.carlada.models.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageService {
    List<ImageDTO> getAllImgFromListing(Long listingId);
    ImageDTO getImgFromListing(Long listingId, Long imgId);
    void deleteImgFromListing(Long listingId, Long imgId, String username) throws IOException;
    Image addImgToListing(Long listingId, MultipartFile file, String username) throws IOException;
    Image setThumbnailForListing(Long listingId, MultipartFile file, String username) throws IOException;
}