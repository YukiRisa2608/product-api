package ra.module05api.controller.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.module05api.service.impl.CartService;

@RestController
@RequestMapping("/api.com/v2/customer/carts")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping
    public ResponseEntity<?> getAllProductCart() {
        return ResponseEntity.ok(cartService.getAllProductCart());
    }

    @PostMapping
    public ResponseEntity<?> updateProductQuantity(@RequestParam("productId") Long productId,
                                                   @RequestParam("quantity") Integer quantity) {
        cartService.updateProductQuantity(productId, quantity);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<?> deleteProductInCart(@RequestParam("productId") Long productId) {
        cartService.deleteProduct(productId);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/clear")
    public ResponseEntity<?> clearCart() {
        cartService.clearCart();

        return ResponseEntity.ok().build();
    }

    @PostMapping("/purchase")
    public ResponseEntity<?> purchase() {
        cartService.purchase();

        return ResponseEntity.ok().build();
    }

}
