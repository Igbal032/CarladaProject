package az.code.carlada.models;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer year;
    private Double mileage;
    private Boolean creditOption;
    private Boolean barterOption;
    private Boolean leaseOption;
    private Boolean cashOption;
    private Double price;
    @ManyToOne
    @JoinColumn(name = "model_id", nullable = false)
    private Model model;
    @OneToOne(mappedBy = "car")
    private CarDetail carDetail;
    @OneToOne
    private Listing listing;
}
