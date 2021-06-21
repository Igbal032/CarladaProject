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
    private Long id;
    private Long makeId;
    private Long modelId;
    private Integer year;
    private Integer price;
    private Integer mileage;
    private String fuelType; // enum
    private String bodyType; // enum
    private String color; // enum
    private Long cityId;
    private String gearBox; // enum
    private Boolean auto_pay; // default false
    private Boolean creditOption; // nullable
    private Boolean barterOption; // nullable
    private Boolean leaseOption; // nullable
    private Boolean cashOption; // nullable
    private String description;
    private String type; //enum ->  new, standart, vip
    private String thumbnailUrl;
    private List<Long> carSpecIds;
}
