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
    private Long id;
    private String makeName;
    private String modelName;
    private String thumbnailUrl;
    private String cityName;
    private Integer price;
    private Integer mileage;
    private Integer year;
    private LocalDateTime updatedAt;
}
