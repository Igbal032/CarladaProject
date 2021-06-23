package az.code.carlada.services;

import az.code.carlada.daos.ImageDAO;
import az.code.carlada.dtos.ImageDTO;
import az.code.carlada.models.Image;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ImageServiceImpl implements ImageService{

    @Value("${firebase.project.id}")
    public String projectId;
    @Value("${firebase.bucket.url}")
    public String bucket;
    ImageDAO imageDAO;
    Storage storage;

    public ImageServiceImpl(ImageDAO imageDAO) {
        this.imageDAO = imageDAO;
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
    public Image addImgToListing(Long listingId, MultipartFile file) throws IOException {
        String imageName = ImageUtil.generateFileName(file.getOriginalFilename());
        Map<String, String> map = new HashMap<>();
        map.put("firebaseStorageDownloadTokens", imageName);
        BlobId blobId = BlobId.of(bucket, imageName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setMetadata(map)
                .setContentType(file.getContentType())
                .build();
        storage.create(blobInfo, file.getInputStream());
        return imageDAO.addImgToListing(listingId,imageName);
    }

    @Override
    public void deleteImgFromListing(Long listingId, Long imgId) throws IOException {
        Image image = imageDAO.findImageById(imgId);
        Credentials credentials = GoogleCredentials.fromStream(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("firebase.json")));
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        BlobId blobId = BlobId.of(bucket, image.getName());
        storage.delete(blobId);
        imageDAO.deleteImgFromListing(listingId, imgId);
    }
}