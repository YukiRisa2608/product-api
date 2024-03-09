package ra.module05api.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ra.module05api.security.jwt.JwtAuthTokenFilter;
import ra.module05api.security.jwt.JwtProvider;
import ra.module05api.security.principle.UserDetailsServiceCustom;

@Configuration
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig {
    @Bean
    public JwtEntryPoint jwtEntryPoint() {
        return new JwtEntryPoint();
    } // xu li loi lien quan toi authentication

    ;
    @Autowired
    private JwtProvider jwtProvider; // bo cung cap 3 chuc nang cua jwt

    @Autowired
    private UserDetailsServiceCustom userDetailsServiceCustom; // cung cap chuc nang loadByUsername

    @Bean
    public JwtAuthTokenFilter jwtAuthTokenFilter() { // laf 1 tang, hay 1 mang loc request gui len
        return new JwtAuthTokenFilter(jwtProvider, userDetailsServiceCustom);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsServiceCustom);
        return provider;
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration auth) throws Exception {
        return auth.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // phi trạng thai
                .authorizeHttpRequests(
                        auth -> auth.requestMatchers("/api.com/v2/auth/**").permitAll()
                                .requestMatchers("/api.com/v5/admin/**").hasAuthority("ROLE_ADMIN")
                                .requestMatchers("/api.com/v2/admin/**").hasAuthority("ROLE_ADMIN")
                                .requestMatchers("/api.com/v2/user/**").hasAuthority("ROLE_USER")
                                .requestMatchers("/api.com/v2/mod/**").hasAuthority("ROLE_MOD")
                                .requestMatchers("/api.com/v2/user-mod/**").hasAnyAuthority("ROLE_USER", "ROLE_MOD")
                                .anyRequest().authenticated()
                );
        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(jwtAuthTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();

    }

}
