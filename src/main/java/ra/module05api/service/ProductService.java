package ra.module05api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ra.module05api.dto.PageDto;
import ra.module05api.entity.Product;
import ra.module05api.exception.ResourceNotFoundException;
import ra.module05api.repository.ProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final ProductRepository productRepository;

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public PageDto findAllWithPagination(Pageable pageable) {
        Pageable custom = PageRequest.of(pageable.getPageNumber(),5,pageable.getSort());
        Page<Product> page = productRepository.findAll(custom);
        return PageDto.builder().data(page.getContent())
                .pages(page.getTotalPages())
                .hasNext(page.hasNext())
                .hasPrev(page.hasPrevious())
                .size(page.getSize())
                .number(page.getNumber())
                .totalElements(page.getTotalElements())
                .sort(page.getSort())
                .build();
    }

    @Override
    public Product findById(Long id) throws ResourceNotFoundException {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found");
        }
        productRepository.deleteById(id);
    }
}
