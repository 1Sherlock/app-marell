package uz.soft.cosmos.appmarellserver.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.soft.cosmos.appmarellserver.entity.User;
import uz.soft.cosmos.appmarellserver.repository.RoleRepository;
import uz.soft.cosmos.appmarellserver.repository.UserRepository;

/**
 * Created by Sherlock on 09.04.2020.
 */

@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Value("${spring.datasource.initialization-mode}")
    String initialMode;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void run(String... strings) throws Exception {
        if (initialMode.equals("always")) {
            User user = new User("+998934366331", passwordEncoder.encode("root123"), "Muxammatov", "Nizom", roleRepository.findAll(), "nizom702@gmail.com", "", "Axadovich", "sherlock");
            userRepository.save(user);
            userRepository.save(new User("+998934366332", passwordEncoder.encode("root123"), "Muxammatov1", "Nizom1", roleRepository.findAll(), "nizom7021@gmail.com", "", "Axadovich", "sherlock1"));
            userRepository.save(new User("+998934366333", passwordEncoder.encode("root123"), "Muxammatov2", "Nizom2", roleRepository.findAll(), "nizom7022@gmail.com", "", "Axadovich", "sherlock2"));
            userRepository.save(new User("+998934366334", passwordEncoder.encode("root123"), "Muxammatov3", "Nizom3", roleRepository.findAll(), "nizom7023@gmail.com", "", "Axadovich", "sherlock3"));
            userRepository.save(new User("+998934366335", passwordEncoder.encode("root123"), "Muxammatov4", "Nizom4", roleRepository.findAll(), "nizom7024@gmail.com", "", "Axadovich", "sherlock4"));
        }
    }
}
