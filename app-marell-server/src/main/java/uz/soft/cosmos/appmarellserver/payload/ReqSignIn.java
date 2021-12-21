package uz.soft.cosmos.appmarellserver.payload;

import lombok.Data;


@Data
public class ReqSignIn {
    private String phoneNumber;

    private String password;
}
