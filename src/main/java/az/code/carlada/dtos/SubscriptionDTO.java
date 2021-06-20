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
    private Integer minMileage;
    private Integer maxMileage;
    private String fuelType;
    private String bodyType;
    private Boolean loanOption;
    private Boolean leaseOption;
    private Boolean cashOption;
    private Boolean barterOption;
    private String color;
    private List<Long> specs;
}
