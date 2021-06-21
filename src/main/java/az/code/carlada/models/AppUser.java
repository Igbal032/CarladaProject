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
@Table(name = "appusers")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String fullName;
    private String email;
    private String phone;
    private Double amount;
    @JsonManagedReference
    @OneToMany(mappedBy="appUser")
    private List<Transaction> transactions;
    @JsonManagedReference
    @OneToMany(mappedBy="appUser")
    private List<Listing> listings;
    @OneToMany(mappedBy="appUser")
    private List<Subscription> subscriptions;
}
