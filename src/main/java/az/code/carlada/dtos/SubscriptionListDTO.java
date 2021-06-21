package az.code.carlada.dtos;

import lombok.Data;
import az.code.carlada.enums.BodyType;
import az.code.carlada.enums.Color;
import az.code.carlada.enums.FuelType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
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
    private Integer minMileage;
    private Integer maxMileage;
    private FuelType fuelType;
    private BodyType bodyType;
    private Boolean loanOption;
    private Boolean leaseOption;
    private Boolean cashOption;
    private Boolean barterOption;
    private Color color;
    private List<CarSpecDTO> specs;
    private LocalDateTime creationDate;
}
