package az.code.carlada.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class ListingCreationDTO {      // CREATE OLDUQDAN SONRA RESPONSE ListingGetDTO OLARAQ QAYIDACAQ
    Long id;
    Long makeId;
    Long modelId;
    Integer year;
    Integer price;
    Integer mileage;
    String fuelType; // enum
    String bodyType; // enum
    String color; // enum
    Long cityId;
    String gearBox; // enum
    Boolean auto_pay; // default false
    Boolean creditOption; // nullable
    Boolean barterOption; // nullable
    Boolean leaseOption; // nullable
    Boolean cashOption; // nullable
    String description;
    String type; //enum ->  new, standart, vip
    String thumbnailUrl;
    List<Long> carSpecIds;
}