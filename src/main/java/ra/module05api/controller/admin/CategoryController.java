package ra.module05api.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.module05api.dto.CategoryDto;
import ra.module05api.dto.DataResponseSuccess;
import ra.module05api.dto.DataResponseError;
import ra.module05api.exception.ResourceNotFoundException;
import ra.module05api.service.ICategoryService;

@RestController
@RequestMapping("/api.com/v2/admin/categories")
@CrossOrigin(origins = "*") // Cho phép tất cả origins
@RequiredArgsConstructor
public class CategoryController {
    private final ICategoryService categoryService;

    //get all
    @GetMapping
    public ResponseEntity<?> getAllCategories() {
        return ResponseEntity.ok(new DataResponseSuccess(categoryService.findAll(), HttpStatus.OK));
    }

    //Get all with page
    @GetMapping("/pagination")
    public ResponseEntity<?> getAllCategoriesWithPagination(Pageable pageable) {
        return ResponseEntity.ok(new DataResponseSuccess(categoryService.findAllWithPagination(pageable), HttpStatus.OK));
    }

    //Get by id
    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Long id) {
        try {
            CategoryDto categoryDto = categoryService.findById(id);
            return ResponseEntity.ok(new DataResponseSuccess(categoryDto, HttpStatus.OK));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.badRequest().body(new DataResponseError(ex.getMessage(), HttpStatus.BAD_REQUEST));
        }
    }

    //Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable Long id) {
        try {
            categoryService.delete(id);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.badRequest().body(new DataResponseError(ex.getMessage(), HttpStatus.BAD_REQUEST));
        }
    }

    //Add
    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody CategoryDto categoryDto) {
        CategoryDto newCategory = categoryService.save(categoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new DataResponseSuccess(newCategory, HttpStatus.CREATED));
    }

    //Update
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @RequestBody CategoryDto categoryDto) {
            categoryDto.setId(id);
            CategoryDto updatedCategory = categoryService.save(categoryDto);
            return ResponseEntity.ok(new DataResponseSuccess(updatedCategory, HttpStatus.OK));
    }

    //Toggle
    @PostMapping("/toggle-status/{id}")
    public ResponseEntity<?> toggleStatus(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.toggleStatus(id));
    }
}
