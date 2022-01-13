package uz.soft.cosmos.appmarellserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.soft.cosmos.appmarellserver.entity.User;
import uz.soft.cosmos.appmarellserver.payload.ApiResponse;
import uz.soft.cosmos.appmarellserver.payload.ResUser;
import uz.soft.cosmos.appmarellserver.repository.RoleRepository;
import uz.soft.cosmos.appmarellserver.repository.UserRepository;
import uz.soft.cosmos.appmarellserver.security.CurrentUser;

import java.util.List;

/**
 * Created by Sherlock on 13.01.2022.
 */
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(@CurrentUser User currentUser) {
        try {
            return ResponseEntity.ok(new ResUser(
                            currentUser.getId(),
                            currentUser.getPhoneNumber(),
                            currentUser.getLastName(),
                            currentUser.getFirstName(),
                            currentUser.getPatron(),
                            currentUser.getLogin(),
                            currentUser.getRoles(),
                            currentUser.getEmail(),
                            currentUser.getInviteId()
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(false, e.getMessage()));
        }
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/getUsers")
    public ResponseEntity<?> getUsers() {
        try {
            List<User> all = userRepository.findAll();
            return ResponseEntity.ok(new ApiResponse(true, "success", all));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(false, e.getMessage()));
        }
    }




}