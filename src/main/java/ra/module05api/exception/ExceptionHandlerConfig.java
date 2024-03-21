package ra.module05api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ra.module05api.dto.DataResponseError;

@RestControllerAdvice
public class ExceptionHandlerConfig {

    @ExceptionHandler(value = {NotFoundException.class})
    protected ResponseEntity<DataResponseError> handleNotFoundException(NotFoundException notFoundException) {
        return ResponseEntity
                .badRequest()
                .body(new DataResponseError(notFoundException.getMessage(), HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(value = {InvalidException.class})
    protected ResponseEntity<DataResponseError> handleInvalidException(InvalidException invalidException) {
        return ResponseEntity
                .badRequest()
                .body(new DataResponseError(invalidException.getMessage(), HttpStatus.BAD_REQUEST));
    }


    @ExceptionHandler(value = {UnAuthorizationException.class})
    protected ResponseEntity<DataResponseError> handleUnAuthorizationException(UnAuthorizationException unAuthorizationException) {
        return ResponseEntity
                .badRequest()
                .body(new DataResponseError(unAuthorizationException.getMessage(), HttpStatus.UNAUTHORIZED));
    }

}
