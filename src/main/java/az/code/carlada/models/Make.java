package az.code.carlada.models;
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
@Table(name = "makes")
public class Make {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String makeName;
    @JsonManagedReference
    @OneToMany(mappedBy="make"
            ,cascade = CascadeType.ALL)
    private List<Model> models;
    @OneToMany(mappedBy="model")
    private List<Subscription> subscriptions;
}
