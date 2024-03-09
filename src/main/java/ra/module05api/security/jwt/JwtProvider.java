package ra.module05api.security.jwt;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j // ghi log ca thông tin ứng dụng
public class JwtProvider {
    @Value("${jwt.secret-key}")
    private String SECRET_KEY;
    @Value("${jwt.expired}")
    private long EXPIRED;

    // tạo token
    public String generateToken(UserDetails userDetails) {
        Date today = new Date();
        return Jwts.builder().setSubject(userDetails.getUsername()) // mã hóa username
                .setIssuedAt(today)
                .setExpiration(new Date(today.getTime() + EXPIRED))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    // validate token
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            log.error("JWT: message error expired:", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT: message error unsupported:", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("JWT: message error not formated:", e.getMessage());
        } catch (SignatureException e) {
            log.error("JWT: message error signature not math:", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT: message claims empty or argument invalid: ", e.getMessage());
        }
        return false;
    }

    // giải mã lây ra username
    public String getUserNameFromToken(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }
}
