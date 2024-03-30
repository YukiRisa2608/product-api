package ra.module05api.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ra.module05api.converter.PageConverter;
import ra.module05api.converter.ProductConverter;
import ra.module05api.dto.PageDto;
import ra.module05api.dto.ProductDto;
import ra.module05api.dto.request.SearchProductPayload;
import ra.module05api.entity.Category;
import ra.module05api.entity.Product;
import ra.module05api.exception.InvalidException;
import ra.module05api.exception.ResourceNotFoundException;
import ra.module05api.repository.ProductRepository;
import ra.module05api.service.IProductService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductConverter productConverter;
    private final UploadService uploadService;
    private final ModelMapper modelMapper;

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
    public PageDto findAllActiveProductsWithPagination(int page) {
        if (page <= 0) {
            page = 1;
        }
        Pageable customPageable = PageRequest.of(page-1, 4);
        Page<Product> pageData = productRepository.findAll(customPageable);
        List<ProductDto> dtos = new ArrayList<>();
        for (Product product : pageData.getContent()) {
            dtos.add(productConverter.entityToDto(product));
        }

        return PageDto.builder()
                .data(dtos)
                .pages(pageData.getTotalPages())
                .hasNext(pageData.hasNext())
                .hasPrev(pageData.hasPrevious())
                .size(pageData.getSize())
                .number(pageData.getNumber())
                .totalElements(pageData.getTotalElements())
                .sort(pageData.getSort())
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

        // validate product name
        validateProductName(product);

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

        // validate product name
        validateProductName(updatedProduct);

        // Chuyển đổi và trả về ProductDto
        return productConverter.entityToDto(productRepository.save(updatedProduct));
    }

    // toggle status
    @SneakyThrows
    public String toggleStatus(Long id) {
        // Find product by id
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        product.setStatus(!product.getStatus());

        productRepository.save(product);

        return "Oke";
    }


    public PageDto findSortedActiveProducts(int page, int size, String sortDirection) {
        Sort sort = Sort.by("price");
        sort = sortDirection.equalsIgnoreCase("desc") ? sort.descending() : sort.ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Product> products = productRepository.findByStatusIsTrue(pageable);
        return PageConverter.convertPageToPageDto(products, ProductDto.class);
    }

    //search
    public PageDto search(SearchProductPayload payload) {
        List<Product> listProduct = new ArrayList<>();
        // Default sort by id
        Sort sort = Sort.by("id").ascending();
        if (payload.getSortBy() != null) {
            if (payload.getSortBy().equalsIgnoreCase("asc")) {
                sort = Sort.by("price").ascending();
            } else {
                sort = Sort.by("price").descending();
            }
        }
        Pageable pageable = PageRequest.of(payload.getPage() == null ? 0 : payload.getPage(), 4, sort);

        Page<Product> products;
        // Neu truyen category id va keyword
        if (payload.getCategoryId() != null && !payload.getKeyword().isEmpty()) {
            products= productRepository.findAllByStatusIsTrueAndCategory_IdAndProductNameContainingIgnoreCase(
                    pageable, payload.getCategoryId(), payload.getKeyword()
            );
        } else if (payload.getCategoryId() != null) {
            // Neu truyen category va khong truyen keyword
            products= productRepository.findAllByStatusIsTrueAndCategory_Id(
                    pageable, payload.getCategoryId()
            );
        } else if (!payload.getKeyword().isEmpty()) {
            // Neu khong truyen category va truyen keyword
            products= productRepository.findAllByStatusIsTrueAndProductNameContainingIgnoreCase(
                    pageable, payload.getKeyword()
            );
        } else {
            // khong truyen category va khong truyen keyword
            products= productRepository.findByStatusIsTrue(pageable);
        }

        return PageConverter.convertPageToPageDto(products, ProductDto.class);
    }

    private void validateProductName(Product product) {
        Product productByName = productRepository.findByProductName(product.getProductName());
        if (product.getId() == null) {
            // Case create
            if (productByName != null) {
                throw new InvalidException("Product name is duplicate");
            }
            return;
        }
        // case update
        if (productByName!= null && !productByName.getId().equals(product.getId())) {
            throw new InvalidException("Product name is duplicate");
        }
    }


}
