package ra.module05api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.module05api.dto.PageDto;
import ra.module05api.entity.Product;
import ra.module05api.exception.ResourceNotFoundException;
import ra.module05api.service.IProductService;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final IProductService productService;

    // API lấy về danh sách tất cả sản phẩm với phân trang
    @GetMapping
    public ResponseEntity<PageDto> findAllProducts(Pageable pageable) {
        return new ResponseEntity<>(productService.findAllWithPagination(pageable), HttpStatus.OK);
    }

    // Tìm kiếm sản phẩm theo ID
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(productService.findById(id));
    }

    // Xóa sản phẩm theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) throws ResourceNotFoundException {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Thêm mới sản phẩm
    @PostMapping
    public ResponseEntity<Product> createNewProduct(@RequestBody Product product) {
        Product createdProduct = productService.save(product);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    // Cập nhật thông tin sản phẩm theo ID
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product, @PathVariable Long id) throws ResourceNotFoundException {
        Product existingProduct = productService.findById(id);
        // Cập nhật thông tin sản phẩm dựa trên thông tin gửi lên từ client
        // Ví dụ: existingProduct.setName(product.getName());
        // Tiếp tục cập nhật các trường khác tương tự...
        Product updatedProduct = productService.save(existingProduct);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }
}
