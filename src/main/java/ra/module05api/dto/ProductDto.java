package ra.module05api.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductDto {

    private Long productId;

    @NotNull
    private Long categoryId;

    @NotBlank
    @Size(min = 3)
    private String productName;

    @DecimalMin(value = "0.01")
    private Double price;

    @NotBlank
    private String description;

    @Min(1)
    private Integer quantity;

    private MultipartFile file;

}
