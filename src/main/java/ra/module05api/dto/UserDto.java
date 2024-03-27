package ra.module05api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @NotNull
    private String username;

    private String fullName;

    @NotNull
    private String password;

    @NotNull
    private String email;

    @NotNull
    private Long roleId;

}
