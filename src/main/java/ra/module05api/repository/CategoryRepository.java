package ra.module05api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.module05api.entity.Category;

import java.util.List;


public interface CategoryRepository extends JpaRepository<Category, Long> {
}
