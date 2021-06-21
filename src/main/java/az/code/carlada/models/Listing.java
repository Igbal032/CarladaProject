package az.code.carlada.models;

import az.code.carlada.enums.Status;
import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
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
    private Boolean autoPay;
    private Boolean isActive;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @JsonBackReference
    @OneToOne(mappedBy = "listing", cascade = CascadeType.ALL)
    private Car car;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="appuser_id")
    private AppUser appUser;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="city_id")
    private City city;
    @OneToMany(mappedBy="listing"
            ,cascade = CascadeType.ALL)
    private List<Images> images;
}