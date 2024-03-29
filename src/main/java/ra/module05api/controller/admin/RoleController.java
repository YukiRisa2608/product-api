package ra.module05api.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ra.module05api.service.impl.RoleService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api.com/v2/admin/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<?> getRoles() {
        return ResponseEntity.ok(roleService.getAllRole());
    }

}
