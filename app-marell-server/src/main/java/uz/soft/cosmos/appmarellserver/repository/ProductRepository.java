package uz.soft.cosmos.appmarellserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.soft.cosmos.appmarellserver.entity.Product;

import java.util.List;
import java.util.UUID;

/**
 * Created by Sherlock on 24.08.2021.
 */
public interface ProductRepository extends JpaRepository<Product, UUID> {
    Boolean existsByName(String name);
    List<Product> findAllByOrderByCreatedAtDesc();
}
