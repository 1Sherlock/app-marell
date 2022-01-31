package uz.soft.cosmos.appmarellserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.soft.cosmos.appmarellserver.entity.Category;
import uz.soft.cosmos.appmarellserver.entity.Partner;
import uz.soft.cosmos.appmarellserver.projection.CustomPartner;

import java.util.List;
import java.util.UUID;

public interface PartnerRepository extends JpaRepository<Partner, UUID> {
    List<CustomPartner> findAllByOrderByCreatedAtDesc();
}

