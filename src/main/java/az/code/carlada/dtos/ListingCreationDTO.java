package az.code.carlada.dtos;

import java.util.List;

public class ListingCreationDTO {      // CREATE OLDUQDAN SONRA RESPONSE ListingGetDTO OLARAQ QAYIDACAQ
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
    List<Integer> carSpecIds;
}
