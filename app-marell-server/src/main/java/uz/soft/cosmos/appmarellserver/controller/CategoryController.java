package uz.soft.cosmos.appmarellserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.soft.cosmos.appmarellserver.entity.Category;
import uz.soft.cosmos.appmarellserver.payload.ApiResponse;
import uz.soft.cosmos.appmarellserver.payload.ReqNameId;
import uz.soft.cosmos.appmarellserver.repository.CategoryRepository;

import java.util.UUID;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    private CategoryRepository categoryRepository;


    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/save")
    public HttpEntity<?> save(@RequestBody ReqNameId reqNameId) {
        try {
            Category category;

            if (reqNameId.getId() == null)
                category = new Category();
            else
                category = categoryRepository.getOne(reqNameId.getId());

            category.setName(reqNameId.getName());

            categoryRepository.save(category);

            if (reqNameId.getId() == null) {
                return ResponseEntity.ok(new ApiResponse(true, "Добавлено"));
            } else {
                return ResponseEntity.ok(new ApiResponse(true, "Изменено"));
            }
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(false, e.getMessage()));
        }
    }

    @GetMapping
    public HttpEntity<?> getAll() {
        return ResponseEntity.ok(new ApiResponse(true, "", categoryRepository.findAllByOrderByCreatedAtDesc()));
    }

    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable UUID id) {
        try {
            categoryRepository.deleteById(id);
            return ResponseEntity.ok(new ApiResponse(true, "Удалено"));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(false, e.getMessage()));
        }
    }
}
