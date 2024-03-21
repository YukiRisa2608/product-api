package ra.module05api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.module05api.entity.Cart;
import ra.module05api.entity.User;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    Cart findByUser_Id(Long userId);

}
