package az.code.carlada.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransactionListDto {
    private Integer id;
    private Long listingid; // nullable
    private Double amount;
    private LocalDateTime createdAt;
}
