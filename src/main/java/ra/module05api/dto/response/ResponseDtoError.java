package ra.module05api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ResponseDtoError {
    private Object message;
    private HttpStatus errorStatus;
}
