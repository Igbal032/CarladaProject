package az.code.carlada.models;

import az.code.carlada.enums.Status;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "listings")
public class Listing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Status type;
    private String thumbnailUrl;
    private LocalDate creationDate;
    private LocalDate updateDate;
    @OneToOne(mappedBy = "listing")
    private Car car;
    @OneToMany(mappedBy="listing"
            ,cascade = CascadeType.ALL)
    private List<Images> images;
}
