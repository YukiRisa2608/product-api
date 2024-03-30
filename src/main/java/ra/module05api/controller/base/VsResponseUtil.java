package ra.module05api.controller.base;

import com.google.api.Http;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class VsResponseUtil {
    public static ResponseEntity<RestData<?>> ok(Object data) {
        return ok(data, HttpStatus.OK);
    }

    public static ResponseEntity<RestData<?>> okMessage(String message) {
        RestData<String> response = new RestData<>();
        response.setMessage(message);
        return ok(response, HttpStatus.OK);
    }

    public static ResponseEntity<RestData<?>> ok(Object data, HttpStatus status) {
        RestData<?> response = new RestData<>(data);
        return new  ResponseEntity<RestData<?>>(response, null, status);
    }

    public static ResponseEntity<RestData<?>> error(String message) {
        RestData<?> response = RestData.error(message);
        return error(message, HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<RestData<?>> error(String message, HttpStatus status) {
        RestData<?> response = RestData.error(message);
        return new ResponseEntity<>(response, status);
    }
}
