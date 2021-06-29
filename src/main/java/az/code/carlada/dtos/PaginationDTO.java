package az.code.carlada.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@Builder(toBuilder = true)
@NoArgsConstructor
public class PaginationDTO<T> {
    private Boolean hasNext;
    private Boolean hasPrevious;
    private Integer pageCount;
    private Integer page;
    private Long count;
    private List<T> items;
}
