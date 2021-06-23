package az.code.carlada.utils;

import org.springframework.util.StringUtils;

import java.util.UUID;

public class ImageUtil {
    public static String generateFileName(String originalFileName) {
        return UUID.randomUUID().toString() + "." + getExtension(originalFileName);
    }

    public static String getExtension(String originalFileName) {
        return StringUtils.getFilenameExtension(originalFileName);
    }

    public static String urlMaker(String imageName){
        return "https://firebasestorage.googleapis.com/v0/b/photos-a7426.appspot.com/o/"+imageName+"?alt=media&token="+imageName;
    }
}
