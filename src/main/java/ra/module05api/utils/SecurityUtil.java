package ra.module05api.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import ra.module05api.repository.UserRepository;
import ra.module05api.security.principle.UserDetailsCustom;

public class SecurityUtil {

    private static final UserRepository userRepository;

    static {
        userRepository = BeanUtil.getBean(UserRepository.class);
    }

    public static UserDetailsCustom getCurrentUserDetail() {
        return (UserDetailsCustom) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static ra.module05api.entity.User getCurrentUser() {
        return userRepository.findByUsername(getCurrentUserDetail().getUsername()).orElse(null);
    }

}
