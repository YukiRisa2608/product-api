package ra.module05api.controller.admin;

import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.module05api.dto.CategoryDto;
import ra.module05api.dto.DataResponseSuccess;
import ra.module05api.dto.UserDto;
import ra.module05api.entity.User;
import ra.module05api.service.impl.UserService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api.com/v2/admin/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

   // get all
    @GetMapping
    public ResponseEntity<?> getList() {
        return ResponseEntity.ok(userService.getListUser());
    }

    //Toggle
    @PostMapping("/toggle-status/{id}")
    public ResponseEntity<?> toggleStatus(@PathVariable Long id) {
        return ResponseEntity.ok(userService.toggleStatus(id));
    }

    //Add
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserDto userDto) {
        User newUser = userService.createNewUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new DataResponseSuccess(newUser, HttpStatus.CREATED));
    }
}