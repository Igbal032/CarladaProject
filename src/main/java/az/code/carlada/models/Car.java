package az.code.carlada.models;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "model_id", nullable = false)
    private Model model;
    @OneToOne(mappedBy = "car", cascade=CascadeType.ALL)
    private CarDetail carDetail;
    @OneToOne(cascade = CascadeType.ALL)
    private Listing listing;
}