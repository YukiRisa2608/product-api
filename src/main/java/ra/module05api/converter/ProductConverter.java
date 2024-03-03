package ra.module05api.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ra.module05api.dto.ProductDto;
import ra.module05api.entity.Category;
import ra.module05api.entity.Product;
import ra.module05api.repository.CategoryRepository;
import ra.module05api.service.UploadService;

import java.io.IOException;

@Component
public class ProductConverter {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UploadService uploadService;

    //Convert dto to entity for add
    public Product addProductConvertDtoToEntity(ProductDto productDto) {
        Product product = new Product();
        product.setProductName(productDto.getProductName());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        product.setQuantity(productDto.getQuantity());
        product.setClassification(productDto.getClassification());
        //Category
        Category category = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + productDto.getCategoryId()));
        product.setCategory(category);
        // tải ảnh lên và set URL ảnh
        if (productDto.getFile() != null && !productDto.getFile().isEmpty()) {
            String imgUrl = uploadService.uploadFileToServer(productDto.getFile());
            product.setImgUrl(imgUrl);
        } else {
            product.setImgUrl(productDto.getImgUrl());
        }
        return product;
    }

    //Convert dto to entity for edit
    public Product updateProductFromDto(ProductDto productDto, Product editProduct) {
        // Cập nhật các thuộc tính cơ bản
        editProduct.setProductName(productDto.getProductName());
        editProduct.setPrice(productDto.getPrice());
        editProduct.setDescription(productDto.getDescription());
        editProduct.setQuantity(productDto.getQuantity());
        editProduct.setClassification(productDto.getClassification());
        // Cập nhật Category
        if (!editProduct.getCategory().getId().equals(productDto.getCategoryId())) {
            Category newCategory = categoryRepository.findById(productDto.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found with id: " + productDto.getCategoryId()));
            editProduct.setCategory(newCategory);
        }
        // tải ảnh mới lên nếu có và cập nhật URL ảnh
        if (productDto.getFile() != null && !productDto.getFile().isEmpty()) {
            String newImgUrl = uploadService.uploadFileToServer(productDto.getFile());
            editProduct.setImgUrl(newImgUrl);
        } else {
            editProduct.setImgUrl(productDto.getImgUrl());
        }
        return editProduct;
    }


    // Chuyển đổi từ Product entity sang ProductDto cho find product
    public ProductDto entityToDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setProductId(product.getId());
        if (product.getCategory() != null) {
            productDto.setCategoryId(product.getCategory().getId());
        }
        productDto.setProductName(product.getProductName());
        productDto.setPrice(product.getPrice());
        productDto.setDescription(product.getDescription());
        productDto.setQuantity(product.getQuantity());
        productDto.setClassification(product.getClassification());
        productDto.setStatus(product.getStatus());
        productDto.setImgUrl(product.getImgUrl());
        productDto.setCreatedDate(product.getCreatedDate());
        productDto.setLastUpdated(product.getLastUpdated());
        return productDto;
    }
}
