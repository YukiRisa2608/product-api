package ra.module05api.entity;

import jakarta.persistence.*;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static jakarta.persistence.TemporalType.TIMESTAMP;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @NotBlank
    @Size(min = 3)
    private String productName;

    @DecimalMin(value = "0.01")
    private Double price;

    @NotBlank
    private String description;

    @Min(1)
    private Integer quantity;

    //@NotBlank
    private String classification;

    private Boolean status = true;

    private String imgUrl;

    @UpdateTimestamp
    @Temporal(TIMESTAMP)
    private LocalDateTime lastUpdated;

    @CreationTimestamp
    @Temporal(TIMESTAMP)
    private LocalDateTime createdDate;
}

