package az.code.carlada.dtos;

import java.time.LocalDateTime;

public class TransactionListDTO {
    private Integer id;
    private Long listingId; // nullable
    private Double amount;
    private LocalDateTime createdAt;
}
