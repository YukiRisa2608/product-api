package ra.module05api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ra.module05api.converter.ProductConverter;
import ra.module05api.dto.PageDto;
import ra.module05api.dto.ProductDto;
import ra.module05api.entity.Product;
import ra.module05api.exception.ResourceNotFoundException;
import ra.module05api.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductConverter productConverter;
    private final UploadService uploadService;

    //Find all
    public List<ProductDto> findAll() {
        List<Product> products = productRepository.findAll();
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product : products) {
            productDtos.add(productConverter.entityToDto(product));
        }
        return productDtos;
    }

    //Find all with page
    public PageDto findAll(Pageable pageable) {
        Pageable customPageable = PageRequest.of(pageable.getPageNumber(), 5, pageable.getSort());
        Page<Product> page = productRepository.findAll(customPageable);
        List<ProductDto> dtos = new ArrayList<>();
        for (Product product : page.getContent()) {
            dtos.add(productConverter.entityToDto(product));
        }

        return PageDto.builder()
                .data(dtos)
                .pages(page.getTotalPages())
                .hasNext(page.hasNext())
                .hasPrev(page.hasPrevious())
                .size(page.getSize())
                .number(page.getNumber())
                .totalElements(page.getTotalElements())
                .sort(page.getSort())
                .build();
    }

    //Find by id
    public ProductDto findById(Long id) throws ResourceNotFoundException {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        return productConverter.entityToDto(product);
    }

    //Delete
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    //Add
    public ProductDto addProduct(ProductDto productDto) {
        //dto to entity
        Product product = productConverter.addProductConvertDtoToEntity(productDto);
        Product savedProduct = productRepository.save(product);
        //entity to dto
        return productConverter.entityToDto(savedProduct);
    }

    //Edit
    //@Transactional đảm bảo tất cả các thay đổi được thực hiện cùng một lúc, hoặc là không có thay đổi nào được thực hiện nếu có lỗi xảy ra,
    // giúp tránh tình trạng dữ liệu không nhất quán.
    @Transactional
    public ProductDto editProduct(ProductDto productDto) {
        Product editProduct = productRepository.findById(productDto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productDto.getProductId()));
        //dto to entity
        Product updatedProduct = productConverter.updateProductFromDto(productDto, editProduct);
        // Chuyển đổi và trả về ProductDto
        return productConverter.entityToDto(productRepository.save(updatedProduct));
    }

}
