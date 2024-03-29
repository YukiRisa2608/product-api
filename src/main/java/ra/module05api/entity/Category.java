package ra.module05api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static jakarta.persistence.TemporalType.TIMESTAMP;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "category")
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String categoryName;

    private Boolean status = true;

    @CreationTimestamp
    @Temporal(TIMESTAMP)
    private LocalDateTime createdDate;

}
