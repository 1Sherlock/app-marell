package uz.soft.cosmos.appmarellserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.soft.cosmos.appmarellserver.entity.Product;
import uz.soft.cosmos.appmarellserver.payload.ApiResponse;
import uz.soft.cosmos.appmarellserver.payload.ReqProduct;
import uz.soft.cosmos.appmarellserver.repository.AttachmentRepository;
import uz.soft.cosmos.appmarellserver.repository.CategoryRepository;
import uz.soft.cosmos.appmarellserver.repository.ProductRepository;

import java.util.UUID;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AttachmentRepository attachmentRepository;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/save")
    public HttpEntity<?> save(@RequestBody ReqProduct reqProduct){
        try {
            Product product;

            if (reqProduct.getId() != null){
                product = productRepository.getOne(reqProduct.getId());
            } else {
                product = new Product();
            }

            product.setName(reqProduct.getName());
            product.setCategory(categoryRepository.getOne(reqProduct.getCategory()));
            product.setDescription(reqProduct.getDescription());
            product.setCount(reqProduct.getCount());
            product.setPrice(reqProduct.getPrice());
            product.setBtc(reqProduct.getBtc());
            product.setType(reqProduct.getType());

            if (reqProduct.getPhoto() != null)
                product.setPhoto(attachmentRepository.getOne(reqProduct.getPhoto()));

            if (reqProduct.getId() == null) {
                return ResponseEntity.ok(new ApiResponse(true, "Добавлено"));
            } else {
                return ResponseEntity.ok(new ApiResponse(true, "Изменено"));
            }

        } catch (Exception e){
            return ResponseEntity.ok(new ApiResponse(false, e.getMessage()));
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteProduct(@PathVariable UUID id){
        try {
            productRepository.deleteById(id);
            return ResponseEntity.ok(new ApiResponse(true, "Удалено"));
        } catch (Exception e){
            return ResponseEntity.ok(new ApiResponse(false, e.getMessage()));
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/all")
    public HttpEntity<?> getAll(){
        try {
            return ResponseEntity.ok(new ApiResponse(true, "success", productRepository.findAllByOrderByCreatedAtDesc()));
        } catch (Exception e){
            return ResponseEntity.ok(new ApiResponse(false, e.getMessage()));
        }
    }


}


