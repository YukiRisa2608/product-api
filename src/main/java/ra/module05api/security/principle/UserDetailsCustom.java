package ra.module05api.security.principle;

import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ra.module05api.entity.User;

import java.util.Collection;
import java.util.List;


@Builder
public class UserDetailsCustom implements UserDetails {
    private Long id;
    private String username;
    private String fullName;
    private String password;
    private boolean status;
    private Collection<? extends GrantedAuthority> authorities;
    public static UserDetailsCustom build(User user){
        List<SimpleGrantedAuthority> authorityList = user.getRoleSet().stream().map(
                        role -> new SimpleGrantedAuthority(role.getRoleName().name()))
                .toList();
        return UserDetailsCustom.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(authorityList)
                .fullName(user.getFullName())
                .status(user.isStatus())
                .build();

    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return status;
    }
}
