package uz.soft.cosmos.appmarellserver.projection;

import org.springframework.beans.factory.annotation.Value;
import uz.soft.cosmos.appmarellserver.component.ValueHelper;
import uz.soft.cosmos.appmarellserver.entity.enums.RoleName;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public interface CustomUser {
    UUID getId();

    boolean isAccountNonExpired();

    boolean isAccountNonLocked();

    Timestamp getCreatedAt();

    boolean isCredentialsNonExpired();

    String getEmail();

    boolean isEnabled();

    String getFirstName();

    String getInviteId();

    String getLastName();

    String getLogin();

    UUID getPartnerId();

    @Value("#{target.getPartnerId()!=null?@ValueHelper.getPartner(target.partnerId):null}")
    String getPartnerName();

    String getPatron();

    String getPhoneNumber();

    List<RoleInfo> getRoles();

    interface RoleInfo {
        Integer getId();

        String getDescription();

        RoleName getName();
    }
}
