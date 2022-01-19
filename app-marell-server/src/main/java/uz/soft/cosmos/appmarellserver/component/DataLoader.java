package uz.soft.cosmos.appmarellserver.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.soft.cosmos.appmarellserver.entity.Partner;
import uz.soft.cosmos.appmarellserver.entity.User;
import uz.soft.cosmos.appmarellserver.repository.PartnerRepository;
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
    PartnerRepository partnerRepository;

    @Autowired
    RoleRepository roleRepository;

    @Value("${spring.datasource.initialization-mode}")
    String initialMode;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void run(String... strings) throws Exception {
        if (initialMode.equals("always")) {
            Partner partner = partnerRepository.save(new Partner("ADMIN", "Partner for admins"));
            User user = new User("+998934366331", passwordEncoder.encode("root123"), "Muxammatov", "Nizom", roleRepository.findAll(), "nizom702@gmail.com", "", "Axadovich", "sherlock",  partner.getId());
            userRepository.save(user);
        }
    }
}
