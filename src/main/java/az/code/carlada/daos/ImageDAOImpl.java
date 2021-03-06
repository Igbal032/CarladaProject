package az.code.carlada.daos;

import az.code.carlada.daos.interfaces.ImageDAO;
import az.code.carlada.exceptions.ImageNotFoundException;
import az.code.carlada.models.Image;
import az.code.carlada.repositories.ImageRepo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ImageDAOImpl implements ImageDAO {
    ImageRepo imageRepository;

    public ImageDAOImpl(ImageRepo imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public Image addImgToListing(Image image, String username) {
        return imageRepository.save(image);
    }

    @Override
    public Image getImgFromListingById(Long listingId, Long imgId) {
        Image image = imageRepository.getImageByListing_IdAndAndId(listingId, imgId);
        if (image == null) {
            throw new ImageNotFoundException("There is not such an image in listing " + listingId);
        }
        return image;
    }

    @Override
    public List<Image> getAllImgFromListing(Long listingId) {
        return imageRepository.getAllByListing_Id(listingId);
    }

    @Override
    public void deleteImgFromListing(Long listingId, Long imgId, String username) {
        imageRepository.deleteById(imgId);
    }

    @Override
    public Image findImageById(Long id) {
        Optional<Image> image = imageRepository.findById(id);
        if (image.isEmpty()) {
            throw new ImageNotFoundException("There is not such an image in listing ");
        }
        return image.get();
    }
}