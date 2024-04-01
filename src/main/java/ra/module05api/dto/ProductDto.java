package ra.module05api.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

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

    private String categoryName;

    @DecimalMin(value = "0.01")
    private Double price;

    @NotBlank
    private String description;

    @Min(1)
    private Integer quantity;

    private String classification;

    private Boolean status;

    private String imgUrl;

    private MultipartFile file;
    
    private LocalDateTime lastUpdated;
    
    private LocalDateTime createdDate;

}
