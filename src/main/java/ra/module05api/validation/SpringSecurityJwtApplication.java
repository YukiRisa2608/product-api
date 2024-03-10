package ra.module05api.validation;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import ra.module05api.entity.Role;
import ra.module05api.entity.RoleName;
import ra.module05api.entity.User;
import ra.module05api.repository.RoleRepository;
import ra.module05api.repository.UserRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import static ra.module05api.entity.RoleName.ROLE_ADMIN;

@SpringBootApplication
public class SpringSecurityJwtApplication {
    @Bean
    public Storage storage() throws IOException {
        InputStream inputStream = new ClassPathResource("firebase-admin-sdk.json").getInputStream();
//        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("firebase-config.json");
        return StorageOptions.newBuilder()
                .setCredentials(GoogleCredentials.fromStream(inputStream))
                .build()
                .getService();
    }
    @Bean
    public ModelMapper modelMapper(){
        return  new ModelMapper();
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityJwtApplication.class, args);
    }
    @Bean
    public CommandLineRunner runner(RoleRepository roleRepository, UserRepository userRepository){
        return args -> {
            // thực  hiện thêm dữ liệu  mẫu ở đây
            Role roleAdmin = new Role(1L, ROLE_ADMIN);
            Role user = new Role(2L, RoleName.ROLE_USER);
            Role mod = new Role(3L, RoleName.ROLE_MOD);
            roleRepository.save(roleAdmin);
            roleRepository.save(user);
            roleRepository.save(mod);

            if(!userRepository.findByUsername("admin").isPresent()) {
                User admin = new User();
                admin.setId(1L);
                admin.setUsername("admin");
                admin.setPassword("$2a$10$/03I4MaPgbDKaVtlscE2Au.J/cTg96wz5vW3HB1ofwXECk/xWudQG");
                admin.setFullName("ADMIN");
                admin.setEmail("admin@gmail.com");
                admin.setStatus(true);
                Set<Role> roleSet = new HashSet<Role>();
                roleSet.add(roleAdmin);
                admin.setRoleSet(roleSet);
                userRepository.save(admin);
            }

        };
    }
}
