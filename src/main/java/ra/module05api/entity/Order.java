package ra.module05api.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ra.module05api.entity.custom_field.JsonNodeConverter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdDate;

    private Double money;

    private String address;

    // Default chua thanh toan: false
    // da thanh toan: true
    private boolean status;

    // Luu tru mang product cua order day
    @Convert(converter = JsonNodeConverter.class)
    @Column(length = 1000000)
    private JsonNode listProduct;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JsonBackReference
    private User user;

}
