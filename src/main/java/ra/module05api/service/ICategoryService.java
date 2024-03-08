package ra.module05api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ra.module05api.dto.CategoryDto;
import ra.module05api.entity.Category;
import ra.module05api.exception.ResourceNotFoundException;

import java.util.List;

public interface ICategoryService {
    List<CategoryDto> findAll();

    Page<CategoryDto> findAllWithPagination(Pageable pageable);

    CategoryDto findById(Long id) throws ResourceNotFoundException;

    CategoryDto save(CategoryDto categoryDto);

    void delete(Long id) throws ResourceNotFoundException;

    Object toggleStatus(Long id);
}

