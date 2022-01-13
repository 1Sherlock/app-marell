package uz.soft.cosmos.appmarellserver.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import uz.soft.cosmos.appmarellserver.entity.Role;

import java.util.List;
import java.util.UUID;



@Data
@AllArgsConstructor
public class ResUser {
    private UUID id;
    private String phoneNumber;
    private String lastName;
    private String firstName;
    private String patron;
    private String login;
    private List<Role> roles;
    private String email;
    private String inviteId;
//    private UUID photo;
}
