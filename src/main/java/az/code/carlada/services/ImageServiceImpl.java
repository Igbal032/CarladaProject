package az.code.carlada.services;

import az.code.carlada.daos.interfaces.ImageDAO;
import az.code.carlada.daos.interfaces.ListingDAO;
import az.code.carlada.dtos.ImageDTO;
import az.code.carlada.models.AppUser;
import az.code.carlada.models.Image;
import az.code.carlada.models.Listing;
import az.code.carlada.repositories.UserRepo;
import az.code.carlada.services.interfaces.ImageService;
import az.code.carlada.utils.ImageUtil;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ImageServiceImpl implements ImageService {

    private final UserRepo userRepository;



    @Value("${firebase.project.id}")
    public String projectId;
    @Value("${firebase.bucket.url}")
    public String bucket;
    ImageDAO imageDAO;
    ListingDAO listingDAO;
    Storage storage;

    public ImageServiceImpl(ImageDAO imageDAO, ListingDAO listingDAO, UserRepo userRepository) {
        this.imageDAO = imageDAO;
        this.listingDAO = listingDAO;
        this.userRepository = userRepository;
    }

    @EventListener
    public void init(ApplicationReadyEvent event) {
        try {
            ClassPathResource serviceAccount = new ClassPathResource("firebase.json");
            storage = StorageOptions.newBuilder().
                    setCredentials(GoogleCredentials.fromStream(serviceAccount.getInputStream())).
                    setProjectId(projectId).build().getService();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<ImageDTO> getAllImgFromListing(Long listingId) {
        return imageDAO.getAllImgFromListing(listingId)
                .stream()
                .map(image -> ImageDTO.builder()
                        .id(image.getId())
                        .name(ImageUtil.urlMaker(image.getName()))
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public ImageDTO getImgFromListing(Long listingId, Long imgId) {
        return ImageDTO.builder()
                .name(ImageUtil.urlMaker(imageDAO.getImgFromListingById(listingId, imgId).getName()))
                .id(imgId)
                .build();
    }

    @Override
    public Image addImgToListing(Long id, MultipartFile file, String username) throws IOException {
        AppUser appUser = userRepository.getAppUserByUsername(username).get();
        listingDAO.getListingsByAppUser(appUser);
        String imageName = ImageUtil.generateFileName(file.getOriginalFilename());
        Map<String, String> map = new HashMap<>();
        map.put("firebaseStorageDownloadTokens", imageName);
        BlobId blobId = BlobId.of(bucket, imageName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setMetadata(map)
                .setContentType(file.getContentType())
                .build();
        storage.create(blobInfo, file.getInputStream());
        return imageDAO.addImgToListing(Image.builder().name(imageName).listing(listingDAO.getListingById(id)).build(), username);
    }

    @Override
    public void deleteImgFromListing(Long listingId, Long imgId, String username) throws IOException {
        AppUser appUser = userRepository.getAppUserByUsername(username).get();
        listingDAO.getListingsByAppUser(appUser);
        Image image = imageDAO.findImageById(imgId);
        Credentials credentials = GoogleCredentials.fromStream(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("firebase.json")));
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        BlobId blobId = BlobId.of(bucket, image.getName());
        storage.delete(blobId);
        imageDAO.deleteImgFromListing(listingId, imgId,username);
    }

    @Override
    public Image setThumbnailForListing(Long listingId, MultipartFile file, String username) throws IOException {
        AppUser appUser = userRepository.getAppUserByUsername(username).get();
        listingDAO.getListingsByAppUser(appUser);
        Image image = addImgToListing(listingId, file, username);
        Listing listing = listingDAO.getListingById(listingId);
        listing.setThumbnailUrl(image.getName());
        listingDAO.saveListing(listing);
        return image;
    }

}