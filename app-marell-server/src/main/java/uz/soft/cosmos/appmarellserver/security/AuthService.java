package uz.soft.cosmos.appmarellserver.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.soft.cosmos.appmarellserver.entity.User;
import uz.soft.cosmos.appmarellserver.entity.enums.RoleName;
import uz.soft.cosmos.appmarellserver.payload.ApiResponse;
import uz.soft.cosmos.appmarellserver.payload.ReqSignIn;
import uz.soft.cosmos.appmarellserver.payload.ReqSignUp;
import uz.soft.cosmos.appmarellserver.repository.RoleRepository;
import uz.soft.cosmos.appmarellserver.repository.UserRepository;


import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        return userRepository.findByPhoneNumber(phoneNumber).orElseThrow(() -> new UsernameNotFoundException("Номер телефона не найдено: " + phoneNumber));
    }

    public UserDetails loadUserById(UUID userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("ИД не найдено: " + userId));
    }


    public ApiResponse register(ReqSignUp reqSignUp) {
        Optional<User> optionalUser = userRepository.findByPhoneNumber(reqSignUp.getPhoneNumber());

        if (optionalUser.isPresent()) {
            return new ApiResponse(false, "Пользователь с такими данными зарегистрирован!");
        } else {
            userRepository.save(
                    new User(
                            reqSignUp.getPhoneNumber(),
                            passwordEncoder.encode(reqSignUp.getPassword()),
                            reqSignUp.getLastName(),
                            reqSignUp.getFirstName(),
                            roleRepository.findAllByName(RoleName.ROLE_USER),
                            reqSignUp.getEmail(),
                            reqSignUp.getInviteId()
                    ));
            return new ApiResponse(true, "Регистрация прошла успешно.");
        }
    }

    public ApiResponse login(ReqSignIn reqSignIn) {
        Optional<User> optionalUser = userRepository.findByPhoneNumber(reqSignIn.getPhoneNumber());

        if (optionalUser.isPresent()) {

            User user = optionalUser.get();
            if (passwordEncoder.matches(reqSignIn.getPassword(), user.getPassword())) {
                return new ApiResponse(true, "Ma'lumotlar to'g'ri kiritildi!");
            } else {
                return new ApiResponse(false, "Parol xato kiritildi");
            }
        } else {
            return new ApiResponse(false, "Bunday foydalanuvchi ro'yhatdan o'tmagan!");
        }
    }
}
