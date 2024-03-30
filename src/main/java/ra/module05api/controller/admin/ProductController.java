package ra.module05api.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.module05api.controller.base.VsResponseUtil;
import ra.module05api.dto.DataResponseSuccess;
import ra.module05api.dto.ProductDto;
import ra.module05api.dto.request.SearchProductPayload;
import ra.module05api.exception.ResourceNotFoundException;
import ra.module05api.service.impl.ProductService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api.com/v2/admin/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    //get all
    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        Object data = productService.findAll();
        return VsResponseUtil.ok(data);
    }

    // get all with page
//    @GetMapping("/pagination")
//    public ResponseEntity<?> findAllActiveProductsWithPagination(@RequestParam("page") int page) {
//        return ResponseEntity.ok(
//                new DataResponseSuccess(productService.findAllActiveProductsWithPagination(page),
//                        HttpStatus.OK)
//        );
//    }

//    // Tìm sản phẩm theo ID
//    @GetMapping("/{id}")
//    public ResponseEntity<ProductDto> findProductById(@PathVariable Long id) throws ResourceNotFoundException {
//        ProductDto product = productService.findById(id);
//        return ResponseEntity.ok(product);
//    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable Long id) {
        productService.delete(id);
        return VsResponseUtil.okMessage("Delete successfully");
    }

    //Add
    @PostMapping()
    public ResponseEntity<?> createProduct(@ModelAttribute ProductDto productDto) {
        ProductDto newProduct = productService.addProduct(productDto);
        return VsResponseUtil.ok(newProduct);
    }

    //Edit
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @ModelAttribute ProductDto productDto) {
        productDto.setProductId(id);
        ProductDto updatedProduct = productService.editProduct(productDto);
        return VsResponseUtil.ok(updatedProduct);
    }

    //Toggle
    @PostMapping("/toggle-status/{id}")
    public ResponseEntity<?> toggleStatus(@PathVariable Long id) {
        return VsResponseUtil.ok(productService.toggleStatus(id));
    }

    //search
    @GetMapping("/search")
    public ResponseEntity<?> search(SearchProductPayload payload) {
        return VsResponseUtil.ok(productService.search(payload));
    }

}
