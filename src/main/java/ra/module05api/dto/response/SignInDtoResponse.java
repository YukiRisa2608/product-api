package ra.module05api.dto.response;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;
import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class SignInDtoResponse {
    private Long id;
    private String fullName;
    private String username;
    private String email;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    private Date birthDay;
    private List<String> role;
    private String accessToken;
    private final String type = "Bearer Token";
}
