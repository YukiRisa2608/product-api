package ra.module05api.controller.admin;

import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ra.module05api.service.impl.UserService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api.com/v2/admin/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> getList() {
        return ResponseEntity.ok(userService.getListUser());
    }

}
