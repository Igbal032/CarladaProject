package az.code.carlada.daos;

import az.code.carlada.models.Image;
import az.code.carlada.repositories.ImageRepo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ImageDAOImpl implements ImageDAO{
    ImageRepo imageRepository;
    ListingDAO listingDAO;

    public ImageDAOImpl(ImageRepo imageRepository, ListingDAO listingDAO) {
        this.imageRepository = imageRepository;
        this.listingDAO = listingDAO;
    }

    @Override
    public Image addImgToListing(Long id, String name) {
        Image image = new Image();
        image.setListing(listingDAO.getListingById(id));
        image.setName(name);
        return imageRepository.save(image);
    }

    @Override
    public Image getImgFromListingById(Long listingId, Long imgId) {
        Image image = imageRepository.getImageByListing_IdAndAndId(listingId, imgId);
        if(image==null){
            throw new ImageNotFoundException("There is not such an image in listing "+listingId);
        }
        return image;
    }

    @Override
    public List<Image> getAllImgFromListing(Long listingId) {
        return imageRepository.getAllByListing_Id(listingId);
    }

    @Override
    public void deleteImgFromListing(Long listingId, Long imgId) {
        imageRepository.deleteById(imgId);
    }

    @Override
    public Image findImageById(Long id) {
        Optional<Image> image = imageRepository.findById(id);
        if(image.isEmpty()){
            throw new ImageNotFoundException("There is not such an image in listing ");
        }
        return image.get();
    }
}
