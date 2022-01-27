package uz.soft.cosmos.appmarellserver.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.soft.cosmos.appmarellserver.entity.Partner;
import uz.soft.cosmos.appmarellserver.entity.User;
import uz.soft.cosmos.appmarellserver.projection.CustomUser;

import java.util.List;
import java.util.Optional;
import java.util.UUID;



public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByPhoneNumber(String phoneNumber);

    Boolean existsByPhoneNumber(String phoneNumber);

    List<CustomUser> findAllByOrderByCreatedAtDesc();



//    Optional<User> findByEmail(String email);


    Page<User> findAllByPhoneNumberContainsOrLastNameContainsOrFirstNameContainsOrderByCreatedAtDesc(String search1, String search2, String search3, Pageable pageable);
}
