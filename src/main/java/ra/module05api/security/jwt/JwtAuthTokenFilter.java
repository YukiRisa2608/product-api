package ra.module05api.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;
import ra.module05api.security.principle.UserDetailsServiceCustom;

import java.io.IOException;
@RequiredArgsConstructor
@Slf4j
public class JwtAuthTokenFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;
    private final UserDetailsServiceCustom userDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // lấy token ra
        try{
            String token = getTokenFromRequest(request);
            if (token!=null && jwtProvider.validateToken(token)){
                // lây ra username
                String username = jwtProvider.getUserNameFromToken(token);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                // tạo usernamepasswordauthtoken
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth); // da xac thuc bang token
            }
        }catch (Exception e){
            log.error("security filter chain err :",e.getMessage());
        }
        filterChain.doFilter(request,response);// gửi request tiếp tục tới cac thành phần khác

    }
    private String getTokenFromRequest(HttpServletRequest request){
        String header = request.getHeader("Authorization");
        // token có dạng Bearer_...
        if (header!=null && header.startsWith("Bearer ")){
            return header.substring("Bearer ".length());
        }
        return null;
    }
}
