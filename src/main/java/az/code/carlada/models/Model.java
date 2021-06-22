package az.code.carlada.models;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "models")
public class Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String modelName;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="make_id", nullable=false)
    private Make make;
    @OneToMany(mappedBy="model")
    private List<Car> cars;
}
