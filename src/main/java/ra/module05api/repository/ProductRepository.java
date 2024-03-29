package ra.module05api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ra.module05api.dto.ProductDto;
import ra.module05api.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE p.status = true")
    Page<Product> findAllActiveProductsWithPagination(Pageable pageable);
    Page<Product> findByStatusTrue(Pageable pageable);
    Page<Product> findByCategoryIdAndStatusTrue(Long categoryId, Pageable pageable);

    //search
    Page<Product> findByStatusTrueAndProductNameContainingIgnoreCase(Pageable pageable, String keyword);

    Page<Product> findAllByCategory_IdAndProductNameContainingIgnoreCase(Pageable pageable, Long categoryId, String keyword);
    Page<Product> findAllByProductNameContainingIgnoreCase(Pageable pageable, String keyword);
    Page<Product> findAllByCategory_Id(Pageable pageable, Long categoryId);
}
