package ra.module05api.controller.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.module05api.entity.Product;
import ra.module05api.entity.User;
import ra.module05api.exception.UnAuthorizationException;
import ra.module05api.service.impl.CartService;
import ra.module05api.utils.SecurityUtil;

@RestController
@RequestMapping("/api.com/v2/customer/carts")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    //get all
    @GetMapping
    public ResponseEntity<?> getAllProductCart() {
        return ResponseEntity.ok(cartService.getAllProductCart());
    }

    //+-
    @PatchMapping
    public ResponseEntity<?> updateProductQuantity(@RequestParam("productId") Long productId,
                                                   @RequestParam("quantity") Integer quantity) {
        cartService.updateProductQuantity(productId, quantity);
        return ResponseEntity.ok().build();
    }

    //delete
    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProductInCart(@PathVariable Long productId) {
        cartService.deleteProduct(productId);
        return ResponseEntity.ok().build();
    }

    //clear
    @DeleteMapping("/clear")
    public ResponseEntity<?> clearCart() {
        cartService.clearCart();
        return ResponseEntity.ok().build();
    }

    //purchase
    @PostMapping("/purchase")
    public ResponseEntity<?> purchase() {
        cartService.purchase();
        return ResponseEntity.ok().build();
    }

    @PostMapping()
    public ResponseEntity<?> addToCart(@RequestParam("productId") Long productId) {
        return ResponseEntity.ok(cartService.addToCart(productId));
    }

}
