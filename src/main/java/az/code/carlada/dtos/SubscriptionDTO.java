package az.code.carlada.dtos;

import java.time.LocalDateTime;
import java.util.List;

public class SubscriptionDTO {
    private Long subId;
    private String name;
    private Long makeId;
    private Long modelId;
    private Long cityId;
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
    private List<Long> specs;
}
