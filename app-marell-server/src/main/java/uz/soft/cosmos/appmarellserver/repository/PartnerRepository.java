package uz.soft.cosmos.appmarellserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.soft.cosmos.appmarellserver.entity.Partner;

import java.util.UUID;

public interface PartnerRepository extends JpaRepository<Partner, UUID> {

}
