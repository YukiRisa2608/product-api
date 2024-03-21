package ra.module05api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import ra.module05api.entity.Order;
import ra.module05api.entity.ProductCart;

import java.util.List;

public interface ICartService {

    // view all product in cart
    List<ProductCart> getAllProductCart();

    // Insert, update product in cart
    void updateProductQuantity(Long productId, Integer quantity);

    // Delete one product in cart
    void deleteProduct(Long productId);

    // Delete all product in cart
    void clearCart();

    // Purchase
    Order purchase() throws JsonProcessingException;
}
