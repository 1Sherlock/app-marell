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


import java.util.Locale;
import java.util.Optional;
import java.util.Random;
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
            String yourValue = "";

            while (yourValue.length() == 0){
                Random rand = new Random();
                yourValue = String.format((Locale)null, //don't want any thousand separators
                        "1%03d%04d%04d%04d",
                        rand.nextInt(1000),
                        rand.nextInt(10000),
                        rand.nextInt(10000),
                        rand.nextInt(10000));
                if (userRepository.existsBySerial(yourValue)){
                    yourValue = "";
                }
            }
            userRepository.save(
                    new User(
                            reqSignUp.getPhoneNumber(),
                            passwordEncoder.encode(reqSignUp.getPassword()),
                            reqSignUp.getLastName(),
                            reqSignUp.getFirstName(),
                            reqSignUp.getPatron(),
                            roleRepository.findAllByName(RoleName.ROLE_USER),
                            reqSignUp.getEmail(),
                            reqSignUp.getInviteId(),
                            yourValue,
                            reqSignUp.getLogin()
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
