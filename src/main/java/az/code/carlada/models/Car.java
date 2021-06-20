package az.code.carlada.models;

import lombok.*;

import javax.persistence.*;

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
    private Integer mileage;
    private Boolean loanOption;
    private Boolean barterOption;
    private Boolean leaseOption;
    private Boolean cashOption;
    private Integer price;
    @ManyToOne
    @JoinColumn(name = "model_id", nullable = false)
    private Model model;
    @OneToOne(mappedBy = "car")
    private CarDetail carDetail;
    @OneToOne
    private Listing listing;
}
