package ra.module05api.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ra.module05api.dto.PageDto;
import ra.module05api.dto.ProductDto;
import ra.module05api.entity.Product;
import ra.module05api.exception.ResourceNotFoundException;

import java.util.List;

public interface IProductService {

    List<Product> findAll();

    PageDto findAllWithPagination(Pageable pageable);
    PageDto findAllActiveProductsWithPagination(Pageable pageable);
    Page<Product> findAllActiveProducts(Pageable pageable);
    Page<Product> searchActiveProductsByName(String name, Pageable pageable);
    PageDto findSortedActiveProducts(int page, String sortDirection);
    Page<ProductDto> findActiveProductsByCategory(Long categoryId, int page, int size);

    Product findById(Long id) throws ResourceNotFoundException;

    Product save(Product product);

    void delete(Long id) throws ResourceNotFoundException;

    //search
    PageDto searchActiveProductsByKeyword(int page, int size, String keyword);

}

