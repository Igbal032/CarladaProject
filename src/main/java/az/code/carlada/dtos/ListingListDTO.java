package az.code.carlada.dtos;

import lombok.Builder;

import java.time.LocalDateTime;
@Builder(toBuilder = true)
public class ListingListDTO { // list dtos ,  search return dtos
    Long id;
    String makeName;
    String modelName;
    String thumbnailUrl;
    String cityName;
    Integer price;
    Integer mileage;
    Integer year;
    LocalDateTime updatedAt;
}
