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
@Table(name = "cities")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cityName;
    @OneToMany(mappedBy="city"
            ,cascade = CascadeType.ALL)
    private List<Listing> listings;
    @OneToMany(mappedBy="city"
            ,cascade = CascadeType.ALL)
    private List<Subscription> subscriptions;
}
