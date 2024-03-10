package ra.module05api.controller.admin;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api.com/v2/admin")
public class ControllerTest {
    @GetMapping("/auth/v1")
    public ResponseEntity<?> publicAll(){
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }
    @GetMapping("/admin/v1")
    public ResponseEntity<?> admin(){
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }
    @GetMapping("/user/test")
    public ResponseEntity<?> anyRequest(){
        return new ResponseEntity<>("ok user", HttpStatus.OK);
    }

}
