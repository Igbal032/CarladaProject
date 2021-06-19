package az.code.carlada.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PaginationDTO<T> {
    private Boolean hasNext;
    private Boolean hasPrevious;
    private Integer pageCount;
    private Integer page;
    private Long count;
    private List<T> items;
}