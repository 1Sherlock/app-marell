package uz.soft.cosmos.appmarellserver.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Created by Sherlock on 25.01.2022.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqAddUserToPartner {
    private UUID user;
    private UUID partner;

    public ReqAddUserToPartner(UUID user){
        this.user = user;
    }
}
