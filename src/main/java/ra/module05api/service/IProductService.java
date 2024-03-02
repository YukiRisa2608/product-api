package ra.module05api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ra.module05api.dto.PageDto;
import ra.module05api.entity.Product;
import ra.module05api.exception.ResourceNotFoundException;


import java.util.List;

public interface IProductService {

    List<Product> findAll();

    PageDto findAllWithPagination(Pageable pageable);

    Product findById(Long id) throws ResourceNotFoundException;

    Product save(Product product);

    void delete(Long id) throws ResourceNotFoundException;

}

