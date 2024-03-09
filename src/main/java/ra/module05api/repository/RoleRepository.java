package ra.module05api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.module05api.entity.Role;
import ra.module05api.entity.RoleName;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByRoleName(RoleName roleName);
}
