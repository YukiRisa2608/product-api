package ra.module05api.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class UnAuthorizationException extends RuntimeException {

    private HttpStatus status;

    private String message = "UnAuthorization";

}
