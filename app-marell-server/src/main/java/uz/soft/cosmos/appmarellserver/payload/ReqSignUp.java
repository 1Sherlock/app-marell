package uz.soft.cosmos.appmarellserver.payload;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReqSignUp {
    private String lastName;
    private String firstName;
    private String patron;
    private String login;
    private String inviteId;
    private String email;
    private String phoneNumber;
    private String password;
}
