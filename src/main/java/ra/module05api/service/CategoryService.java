package ra.module05api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ra.module05api.dto.CategoryDto;
import ra.module05api.entity.Category;
import ra.module05api.exception.ResourceNotFoundException;
import ra.module05api.repository.CategoryRepository;
import ra.module05api.converter.CategoryConverter;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService{
    private final CategoryRepository categoryRepository;
    private final CategoryConverter categoryConverter;

    public List<CategoryDto> findAll() {
        List<Category> categories = categoryRepository.findAll();
        // Sử dụng CategoryConverter để chuyển đổi sang CategoryDto
        return categories.stream().map(categoryConverter::entityToDto).collect(Collectors.toList());
    }

    public Page<CategoryDto> findAllWithPagination(Pageable pageable) {
        Page<Category> categoryPage = categoryRepository.findAll(pageable);
        // Chuyển đổi mỗi phần tử của trang sang CategoryDto
        return categoryPage.map(categoryConverter::entityToDto);
    }

    public CategoryDto findById(Long id) throws ResourceNotFoundException {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        return categoryConverter.entityToDto(category);
    }

    @Override
    public CategoryDto save(CategoryDto categoryDto) {
        // Chuyển đổi từ CategoryDto sang entity Category
        Category category = categoryConverter.dtoToEntity(categoryDto);
        // Lưu entity Category vào cơ sở dữ liệu
        Category savedCategory = categoryRepository.save(category);
        // Chuyển đổi entity Category lưu được sang CategoryDto và trả về
        return categoryConverter.entityToDto(savedCategory);
    }

    public void delete(Long id) throws ResourceNotFoundException {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Category not found");
        }
        categoryRepository.deleteById(id);
    }
}
