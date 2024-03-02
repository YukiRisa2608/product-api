package ra.module05api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.module05api.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
