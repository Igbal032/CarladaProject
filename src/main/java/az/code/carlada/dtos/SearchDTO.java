package az.code.carlada.dtos;

import az.code.carlada.enums.BodyType;
import az.code.carlada.enums.FuelType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class SearchDTO {
    private Integer make;
    private Integer model;
    private Integer location;
    private Integer minYear;
    private Integer maxYear;
    private Integer minPrice;
    private Integer maxPrice;
    private Integer minMileage;
    private Integer maxMileage;
    private FuelType fuelType;
    private Boolean loanOption;
    private Boolean barterOption;
    private Boolean leaseOption;
    private Boolean cashOption;
    private BodyType bodyType;
    private Integer page;
    private Integer count;
}
