package uz.soft.cosmos.appmarellserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.soft.cosmos.appmarellserver.entity.Role;
import uz.soft.cosmos.appmarellserver.entity.enums.RoleName;

import java.util.List;

//@RepositoryRestResource(path = "role",collectionResourceRel = "list",excerptProjection = CustomRole.class)
public interface RoleRepository extends JpaRepository<Role, Integer> {
    List<Role> findAllByName(RoleName roleName);
    List<Role> findAllByNameIn(List<RoleName> names);
}
