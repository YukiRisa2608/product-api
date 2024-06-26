package ra.module05api.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ra.module05api.dto.CategoryDto;
import ra.module05api.entity.Category;
import ra.module05api.entity.Product;
import ra.module05api.exception.InvalidException;
import ra.module05api.exception.ResourceNotFoundException;
import ra.module05api.repository.CategoryRepository;
import ra.module05api.converter.CategoryConverter;
import ra.module05api.service.ICategoryService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryConverter categoryConverter;

    public List<CategoryDto> findAll() {
        List<Category> categories = categoryRepository.findAll();
        // Sử dụng CategoryConverter để chuyển đổi sang CategoryDto
        return categories.stream().map(categoryConverter::entityToDto).collect(Collectors.toList());
    }


    public List<CategoryDto> findAllWithStatusIsTrue() {
        List<Category> categories = categoryRepository.findAllByStatusIsTrue();
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

    //add
    @Override
    public CategoryDto save(CategoryDto categoryDto) {
        // Chuyển đổi từ CategoryDto sang entity Category
        Category category = categoryConverter.dtoToEntity(categoryDto);

        // Validate name
        validateCategoryName(categoryDto.getId(), category);

        // Lưu entity Category vào cơ sở dữ liệu
        Category savedCategory = categoryRepository.save(category);
        // Chuyển đổi entity Category lưu được sang CategoryDto và trả về
        return categoryConverter.entityToDto(savedCategory);
    }

    private void validateCategoryName(Long id, Category category) {
        Category categoryByName = categoryRepository.findByCategoryName(category.getCategoryName());
        if (id == null) {
            // Case create
            if (categoryByName != null) {
                throw new InvalidException("Category name is duplicate");
            }
            return;
        }
        // case update
        if (categoryByName != null && !categoryByName.getId().equals(id)) {
            throw new InvalidException("Category name is duplicate");
        }
    }

    //delete
    public void delete(Long id) throws ResourceNotFoundException {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Category not found");
        }
        categoryRepository.deleteById(id);
    }

    // toggle
    @SneakyThrows
    public String toggleStatus(Long id) {
        // Find product by id
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        category.setStatus(!category.getStatus());

        categoryRepository.save(category);

        return "Oke";
    }
}
