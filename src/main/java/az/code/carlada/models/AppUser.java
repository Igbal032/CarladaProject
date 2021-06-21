package az.code.carlada.models;

import az.code.carlada.models.Transaction;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
}