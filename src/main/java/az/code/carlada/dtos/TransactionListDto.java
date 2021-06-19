package az.code.carlada.dtos;

import java.time.LocalDateTime;

public class TransactionListDto {
    private Integer id;
    private Long listingId; // nullable
    private Double amount;
    private LocalDateTime createdAt;
}
