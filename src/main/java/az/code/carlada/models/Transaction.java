package az.code.carlada.models;

import az.code.carlada.enums.TransactionType;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
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
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    @ManyToOne
    @JoinColumn(name="appuser_id", nullable=false)
    private AppUser appUser;
    private LocalDate createdDate;
}
