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
@Table(name = "appusers")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String phoneNumber;
    private Double amount;
    @OneToMany(mappedBy="appUser")
    private List<Transaction> transactions;
    @OneToMany(mappedBy="appUser")
    private List<Subscription> subscriptions;
}
