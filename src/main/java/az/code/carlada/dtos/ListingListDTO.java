package az.code.carlada.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
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
