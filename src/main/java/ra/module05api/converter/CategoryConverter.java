package ra.module05api.converter;

import org.springframework.stereotype.Component;
import ra.module05api.dto.CategoryDto;
import ra.module05api.entity.Category;
@Component
public class CategoryConverter {

    // Chuyển đổi từ DTO sang Entity cho thao tác Create và Update
    public Category dtoToEntity(CategoryDto dto) {
        Category category = new Category();
        // Nếu chứa id là update
        if (dto.getId() != null) {
            category.setId(dto.getId());
        }
        //Không có id là tạo mới
        category.setCategoryName(dto.getCategoryName());
        // Status default true và createdDate default system
        return category;
    }

    // Chuyển đổi từ Entity sang DTO cho thao tác Read
    public CategoryDto entityToDto(Category category) {
        CategoryDto dto = new CategoryDto();
        dto.setId(category.getId());
        dto.setCategoryName(category.getCategoryName());
        dto.setStatus(category.getStatus());
        dto.setCreatedDate(category.getCreatedDate());
        return dto;
    }
}

