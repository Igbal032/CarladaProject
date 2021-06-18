package az.code.carlada.models;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "images")
public class Images {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String thumbnailLink;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="listing_id")
    private Listing listing;
}
