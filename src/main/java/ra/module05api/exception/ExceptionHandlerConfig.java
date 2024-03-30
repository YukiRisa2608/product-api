package ra.module05api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import ra.module05api.controller.base.VsResponseUtil;
import ra.module05api.dto.DataResponseError;

@RestControllerAdvice
public class ExceptionHandlerConfig {

    @ExceptionHandler(value = {NotFoundException.class})
    protected ResponseEntity<?> handleNotFoundException(NotFoundException notFoundException) {
        return VsResponseUtil.error(notFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {InvalidException.class})
    protected ResponseEntity<?> handleInvalidException(InvalidException invalidException) {
       return VsResponseUtil.error(invalidException.getMessage(), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(value = {UnAuthorizationException.class})
    protected ResponseEntity<?> handleUnAuthorizationException(UnAuthorizationException unAuthorizationException) {
        return VsResponseUtil.error(unAuthorizationException.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = {HttpClientErrorException.Forbidden.class})
    protected ResponseEntity<?> handleException(Exception ex) {
        return VsResponseUtil.error(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

}
