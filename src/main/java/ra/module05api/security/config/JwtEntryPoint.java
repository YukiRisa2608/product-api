package ra.module05api.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.naming.AuthenticationException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JwtEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        log.error("authentication fail cause :",authException.getMessage());
        response.setHeader("error","authorize");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(403);
        Map<String , String> map =new HashMap<>();
        map.put("error",authException.getMessage());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getOutputStream(),map);
    }
}
