package ra.module05api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.module05api.entity.ProductCart;

import java.util.List;

@Repository
public interface ProductCartRepository extends JpaRepository<ProductCart, Long> {

    List<ProductCart> findAllByCart_Id(Long cartId);

    ProductCart findByCart_IdAndProduct_Id(Long cartId, Long productId);

    void deleteAllByCart_Id(Long cartId);
}
