package az.code.carlada.models;
import az.code.carlada.enums.TransactionType;
import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="appuser_id", nullable=false)
    private AppUser appUser;
    private Double amount;
    private Long listingId;
    private LocalDateTime createdDate;
}
