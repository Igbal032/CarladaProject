package az.code.carlada.services;

import az.code.carlada.dtos.ImageDTO;
import az.code.carlada.models.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageService {
    List<ImageDTO> getAllImgFromListing(Long listingId);
    ImageDTO getImgFromListing(Long listingId, Long imgId);
    void deleteImgFromListing(Long listingId, Long imgId) throws IOException;
    Image addImgToListing(Long listingId, MultipartFile file) throws IOException;
}