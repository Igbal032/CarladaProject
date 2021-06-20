package az.code.carlada.models;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "specifications")
public class Specification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToMany(mappedBy = "carSpecifications",cascade = CascadeType.PERSIST)
    private List<CarDetail> carDetails;
    @ManyToMany(mappedBy = "specs",cascade = CascadeType.PERSIST)
    private List<Subscription> subscriptions;
}