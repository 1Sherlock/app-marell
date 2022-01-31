package uz.soft.cosmos.appmarellserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import uz.soft.cosmos.appmarellserver.entity.Role;
import uz.soft.cosmos.appmarellserver.entity.User;
import uz.soft.cosmos.appmarellserver.entity.enums.RoleName;
import uz.soft.cosmos.appmarellserver.payload.ApiResponse;
import uz.soft.cosmos.appmarellserver.payload.ReqAddUserToPartner;
import uz.soft.cosmos.appmarellserver.payload.ReqChangeRole;
import uz.soft.cosmos.appmarellserver.payload.ResUser;
import uz.soft.cosmos.appmarellserver.projection.CustomUser;
import uz.soft.cosmos.appmarellserver.repository.RoleRepository;
import uz.soft.cosmos.appmarellserver.repository.UserRepository;
import uz.soft.cosmos.appmarellserver.security.CurrentUser;

import java.util.ArrayList;
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
//    public ResponseEntity<?> getUsers(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size, @RequestParam(defaultValue = "") String search) {
    public ResponseEntity<?> getUsers() {
        try {
//            Page<User> all = userRepository.findAllByPhoneNumberContainsOrLastNameContainsOrFirstNameContainsOrderByCreatedAtDesc(search, search, search, PageRequest.of(page, size));
            List<CustomUser> all = userRepository.findAllByOrderByCreatedAtDesc();
            return ResponseEntity.ok(new ApiResponse(true, "success", all));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(false, e.getMessage()));
        }
    }

    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @PostMapping("/changeRole")
    public HttpEntity<?> changeRole(@RequestBody ReqChangeRole reqChangeRole){
        try {
            User user = userRepository.getOne(reqChangeRole.getUserId());

            user.setRoles(roleRepository.findAllByName(reqChangeRole.getRole()));

            if (reqChangeRole.getRole().equals(RoleName.ROLE_USER)){
                user.setPartnerId(null);
            } else {
                user.setPartnerId(reqChangeRole.getPartner());
            }

            userRepository.save(user);

            return ResponseEntity.ok(new ApiResponse(true, "Сохранено"));

        } catch (Exception e){
            return ResponseEntity.ok(new ApiResponse(false, e.getLocalizedMessage()));
        }
    }


//    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
//    @PostMapping("/addUserToPartner")
//    public HttpEntity<?> addUserToPartner(@RequestBody ReqAddUserToPartner reqAddUserToPartner){
//        try {
//            User user = userRepository.getOne(reqAddUserToPartner.getUser());
//
//            user.setPartnerId(reqAddUserToPartner.getPartner());
//            user.setRoles(roleRepository.findAllByName(RoleName.ROLE_PARTNER));
//
//            userRepository.save(user);
//
//            return ResponseEntity.ok(new ApiResponse(true, "Сохранено"));
//        }catch (Exception e){
//            return ResponseEntity.ok(new ApiResponse(false, e.getMessage()));
//        }
//    }
//
//    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN', 'ROLE_PARTNER')")
//    @PostMapping("/deleteUserFromPartner")
//    public HttpEntity<?> deleteUserFromPartner(@RequestBody ReqAddUserToPartner reqAddUserToPartner){
//        try {
//            User user = userRepository.getOne(reqAddUserToPartner.getUser());
//
//            user.setPartnerId(null);
//            user.setRoles(roleRepository.findAllByName(RoleName.ROLE_USER));
//
//            userRepository.save(user);
//
//            return ResponseEntity.ok(new ApiResponse(true, "Сохранено"));
//        }catch (Exception e){
//            return ResponseEntity.ok(new ApiResponse(false, e.getMessage()));
//        }
//    }
}
