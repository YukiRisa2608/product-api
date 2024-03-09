package ra.module05api.validation;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

@SpringBootApplication
public class SpringSecurityJwtApplication {
    @Bean
    public Storage storage() throws IOException {
        InputStream inputStream = new ClassPathResource("firebase-config.json").getInputStream();
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
//    @Bean
//    public CommandLineRunner runner(RoleRepository roleRepository){
//        return args -> {
//            // thực  hiện thêm dữ liệu  mẫu ở đây
//            Role admin = new Role(1L, RoleName.ROLE_ADMIN);
//            Role user = new Role(2L, RoleName.ROLE_USER);
//            Role mod = new Role(3L, RoleName.ROLE_MOD);
//            roleRepository.save(admin);
//            roleRepository.save(user);
//            roleRepository.save(mod);
//        };
//    }

}
