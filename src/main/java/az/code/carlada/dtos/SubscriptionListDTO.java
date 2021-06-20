package az.code.carlada.dtos;

import java.time.LocalDateTime;
import java.util.List;

public class SubscriptionListDTO {
    //parameters -> makeId, modelId, cityId, minYear, maxYear,  minPrice, maxPrice, minMileage, maxMileage,
    // fuel, loanOption(loan, barter, lease, cash) , bodyType, gearBox, type , specs = [...specIds]
    private Long subId;
    private String name;
    private MakeDTO make;
    private ModelDTO model;
    private CityDTO city;
    private Integer minYear;
    private Integer maxYear;
    private Integer minPrice;
    private Integer maxPrice;
    private Double minMileage;
    private Double maxMileage;
    private Integer fuelType;
    private Integer bodyType;
    private Boolean loanOption;
    private Boolean leaseOption;
    private Boolean cashOption;
    private Boolean barterOption;
    private String color;
    private List<CarSpecDTO> specs;
    private LocalDateTime creationDate;
}
