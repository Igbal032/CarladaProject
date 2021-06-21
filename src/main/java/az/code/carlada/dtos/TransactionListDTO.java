package az.code.carlada.dtos;

import az.code.carlada.enums.TransactionType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder(toBuilder = true)
public class TransactionListDTO {
    private Long id;
    private Long listingId; // nullable
    private Double amount;
    private TransactionType transactionType;
    private LocalDateTime createdAt;
}
