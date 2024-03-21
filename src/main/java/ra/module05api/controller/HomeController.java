package ra.module05api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.module05api.dto.PageDto;
import ra.module05api.dto.request.SignInRequest;
import ra.module05api.dto.request.SignUpRequest;
import ra.module05api.dto.response.ResponseDtoSuccess;
import ra.module05api.dto.response.SignInDtoResponse;
import ra.module05api.exception.DataFieldExistException;
import ra.module05api.exception.UsernameOrPasswordException;
import ra.module05api.service.impl.AuthenticationService;
import ra.module05api.service.impl.ProductService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api.com/v2/home")  // công khai
@RequiredArgsConstructor
public class HomeController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<PageDto> getAllActiveProducts(@RequestParam("page") int page) {
        PageDto products = productService.findAllActiveProductsWithPagination(page);
        return ResponseEntity.ok(products);
    }
}
