package uz.soft.cosmos.appmarellserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.soft.cosmos.appmarellserver.entity.Category;

import java.util.List;
import java.util.UUID;

/**
 * Created by Sherlock on 24.08.2021.
 */
public interface CategoryRepository extends JpaRepository<Category, UUID> {
    List<Category> findAllByOrderByCreatedAtDesc();
}
