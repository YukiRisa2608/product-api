package ra.module05api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageDto {
    private Object data;
    private Boolean hasNext, hasPrev;
    private long pages;
    private long totalElements;
    private int number;
    private int size;
    private Sort sort;
}
