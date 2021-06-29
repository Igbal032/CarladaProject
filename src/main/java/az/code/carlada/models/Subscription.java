package az.code.carlada.models;

import az.code.carlada.enums.BodyType;
import az.code.carlada.enums.Color;
import az.code.carlada.enums.FuelType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "subscriptions")
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subId;
    private String name;
    private Integer minYear;
    private Integer maxYear;
    private Integer minPrice;
    private Integer maxPrice;
    private Integer minMileage;
    private Integer maxMileage;
    @Enumerated(EnumType.STRING)
    private FuelType fuelType;
    @Enumerated(EnumType.STRING)
    private BodyType bodyType;
    @Enumerated(EnumType.STRING)
    private Color color;
    private Boolean loanOption;
    private Boolean leaseOption;
    private Boolean cashOption;
    private Boolean barterOption;
    private LocalDateTime creationDate;
    @ManyToOne
    @JoinColumn(name="model_id")
    private Model model;
    @ManyToOne
    @JoinColumn(name="make_id")
    private Make make;
    @ManyToOne
    @JoinColumn(name="city_id")
    private City city;
    @ManyToOne
    @JoinColumn(name="appuser_id")
    private AppUser appUser;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "subscription_specification", joinColumns = {
            @JoinColumn(name = "subscription_id")
    },
            inverseJoinColumns = {
                    @JoinColumn(name = "specification_id")
            })
    private List<Specification> specs;
}
