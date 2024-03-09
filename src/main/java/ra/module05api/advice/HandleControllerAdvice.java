package ra.module05api.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ra.module05api.dto.response.ResponseDtoError;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class HandleControllerAdvice {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleDataBadRequest(MethodArgumentNotValidException ex){
        Map<String , String> map = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(field -> map.put(field.getField(),field.getDefaultMessage()) );
        return new ResponseEntity<>(new ResponseDtoError(map,HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }
}
