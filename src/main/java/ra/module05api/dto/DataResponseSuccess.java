package ra.module05api.dto;

import lombok.*;
import org.springframework.http.HttpStatus;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class DataResponseSuccess {
    private Object data;
    private HttpStatus status;
}
