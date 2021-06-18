package az.code.carlada.dtos;

import java.time.LocalDateTime;
import java.util.List;

public class ListingGetDTO {
    private Long id;
    private AppUserDTO user;
    private MakeDTO make;
    private ModelDTO model;
    private Integer year;
    private Integer price;
    private Integer mileage;
    private String fuelType;
    private String bodyType;
    private String color;
    private CityDTO city;
    private String gearBox;
    private Boolean autoPay;
    private Boolean creditOption;
    private Boolean barterOption;
    private Boolean leaseOption;
    private Boolean cashOption;
    private String description;
    private String type;
    private String thumbnailUrl;
    private List<CarSpecDTO> carSpecs;
    private LocalDateTime updatedAt;
    private Boolean isActive;
}
