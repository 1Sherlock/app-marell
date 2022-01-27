package uz.soft.cosmos.appmarellserver.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.soft.cosmos.appmarellserver.entity.enums.RoleName;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqChangeRole {
    private UUID userId;
    private RoleName role;
    private UUID partner;
}
