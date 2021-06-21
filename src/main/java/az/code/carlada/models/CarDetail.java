package az.code.carlada.models;
import az.code.carlada.enums.BodyType;
import az.code.carlada.enums.Color;
import az.code.carlada.enums.FuelType;
import az.code.carlada.enums.Gearbox;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "car_details")
public class CarDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private FuelType fuelType;
    @Enumerated(EnumType.STRING)
    private BodyType bodyType;
    @Enumerated(EnumType.STRING)
    private Color color;
    @Enumerated(EnumType.STRING)
    private Gearbox gearBox;
    @OneToOne
    private Car car;
    @ManyToMany
    @JoinTable(name = "car_details_specification", joinColumns = {
            @JoinColumn(name = "car_details_id")
    },
    inverseJoinColumns = {
            @JoinColumn(name = "specification_id")
    })
    private List<Specification> carSpecifications;
}
