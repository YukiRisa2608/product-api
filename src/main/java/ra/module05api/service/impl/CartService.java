package ra.module05api.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import ra.module05api.dto.UserCartDto;
import ra.module05api.entity.*;
import ra.module05api.exception.InvalidException;
import ra.module05api.exception.NotFoundException;
import ra.module05api.exception.UnAuthorizationException;
import ra.module05api.repository.*;
import ra.module05api.service.ICartService;
import ra.module05api.utils.SecurityUtil;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class CartService implements ICartService {
    private final CartRepository cartRepository;
    private final ProductCartRepository productCartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final ObjectMapper objectMapper;

    private UserCartDto commonData() {
        User user = SecurityUtil.getCurrentUser();
        if (user == null) {
            throw new UnAuthorizationException();
        }
        Cart cart = cartRepository.findByUser_Id(user.getId());

        // Check cart null
        if (cart == null) {
            cart = new Cart(null, user, new ArrayList<>());
            cart = cartRepository.save(cart);
        }
        return new UserCartDto(user, cart);
    }

    @Override
    public List<ProductCart> getAllProductCart() {
        // Fina user call api
        UserCartDto object = commonData();

        return productCartRepository.findAllByCart_Id(object.getCart().getId());
    }

    @Override
    public void updateProductQuantity(Long productId, Integer quantity) {
        UserCartDto object = commonData();
        User user = object.getUser();
        Cart cart = object.getCart();

        Product product = productRepository.findById(productId).orElse(null);
        // Check product valid
        if (product == null) {
            throw new NotFoundException("Not found product id = " + productId);
        }

        // Check if productId contain in cart
        ProductCart productCart = productCartRepository.findByCart_IdAndProduct_Id(cart.getId(), productId);
        if (productCart == null) {
            if (quantity > 0) {
                productCart = new ProductCart(null, product, cart, quantity);
            } else {
                throw new InvalidException("Product not contain in cart");
            }
        } else {
            // TH khac null
            int newQuantity = productCart.getQuantity() + quantity;
            if (newQuantity <= 0) {
                throw new InvalidException("Quantity must > 0");
            }
            productCart.setQuantity(newQuantity);
        }

        // Save to DB
        productCartRepository.save(productCart);
    }

    @Override
    public void deleteProduct(Long productId) {
        UserCartDto object = commonData();
        User user = object.getUser();
        Cart cart = object.getCart();

        Product product = productRepository.findById(productId).orElse(null);
        // Check product valid
        if (product == null) {
            throw new NotFoundException("Not found product id = " + productId);
        }

        // Check if productId contain in cart
        ProductCart productCart = productCartRepository.findByCart_IdAndProduct_Id(cart.getId(), productId);

        if (productCart == null) {
            throw new NotFoundException("Not found productId = " + productId + " in cart");
        }

        productCartRepository.delete(productCart);
    }

    @Override
    public void clearCart() {
        UserCartDto object = commonData();
        User user = object.getUser();
        Cart cart = object.getCart();

        productCartRepository.deleteAllByCart_Id(cart.getId());
    }

    @Override
    @SneakyThrows
    public Order purchase() {
        UserCartDto object = commonData();
        User user = object.getUser();
        Cart cart = object.getCart();

        List<ProductCart> listProductCart = cart.getProductCarts();
        if (listProductCart.isEmpty()) {
            throw new InvalidException("Your cart is empty");
        }

        ArrayNode listProduct = objectMapper.createArrayNode();
        double money = 0;
        for (ProductCart productCart: listProductCart) {
            Product product = productCart.getProduct();
            if (product.getQuantity() < productCart.getQuantity()) {
                throw new InvalidException("Quantity khong du"); // note sua message
            }
            // Update quantity to DB
            product.setQuantity(product.getQuantity() - productCart.getQuantity());
            productRepository.save(product);

            // Create product for order
            Product tempProduct = product.clone();
            tempProduct.setQuantity(productCart.getQuantity());
            listProduct.add(objectMapper.writeValueAsString(tempProduct));

            // Calcalation money
            money += product.getPrice() * productCart.getQuantity();
        }

        // clear cart
        productCartRepository.deleteByCartId(cart.getId());

        // Create object order
        Order order = new Order();
        order.setCreatedDate(Calendar.getInstance().getTime());
        order.setMoney(money);
        // TODO: Add field address to user or get from parameter
        order.setAddress(user.getUsername());
        order.setStatus(true);
        order.setListProduct(listProduct);
        order.setUser(user);

        return orderRepository.save(order);
    }

    @Override
    public String addToCart(Long productId) {
        User user = SecurityUtil.getCurrentUser();
        if (user == null) {
            throw new UnAuthorizationException();
        }

        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            throw new NotFoundException("Can not found product id = " + productId);
        }

        Cart cart = user.getCart();
        if (cart == null) {
            cart = cartRepository.save(new Cart(null, user, new ArrayList<>()));
            user.setCart(cart);
            userRepository.save(user);
        }

        ProductCart productCart = productCartRepository.findByCart_IdAndProduct_Id(cart.getId(), productId);
        if (productCart == null) {
            // Add new product to cart
            productCart = productCartRepository.save(new ProductCart(null, product, cart, 1));
        } else {
            // Update quantity
            productCart.setQuantity(productCart.getQuantity() + 1);
            productCartRepository.save(productCart);
        }

        return "Success";
    }
}
