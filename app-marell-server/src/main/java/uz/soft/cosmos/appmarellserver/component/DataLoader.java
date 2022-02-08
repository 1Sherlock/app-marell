package uz.soft.cosmos.appmarellserver.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.soft.cosmos.appmarellserver.entity.Attachment;
import uz.soft.cosmos.appmarellserver.entity.AttachmentContent;
import uz.soft.cosmos.appmarellserver.entity.Partner;
import uz.soft.cosmos.appmarellserver.entity.User;
import uz.soft.cosmos.appmarellserver.repository.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

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

    @Autowired
    AttachmentRepository attachmentRepository;

    @Autowired
    AttachmentContentRepository attachmentContentRepository;

    @Override
    public void run(String... strings) throws Exception {
        if (initialMode.equals("always")) {

            Path currentDir = Paths.get(".");
            Path path = Paths.get(currentDir + "/logo.svg");

            Attachment image = new Attachment();
            image.setName(UUID.randomUUID().toString());
            image.setOriginalName("logo.ico");
            image.setSize(Files.size(path));
            image.setContentType(Files.probeContentType(path));
            image = attachmentRepository.save(image);

            byte[] bytes = Files.readAllBytes(path);
            AttachmentContent content = new AttachmentContent();
            content.setContent(bytes);
            content.setAttachment(image);
            attachmentContentRepository.save(content);

            Partner partner = partnerRepository.save(new Partner("Marell", "Partner for admins", image, "Marell"));
            User user = new User("+998934366331", passwordEncoder.encode("root123"), "Muxammatov", "Nizom", roleRepository.findAll(), "nizom702@gmail.com", "", "Axadovich", "sherlock",  partner.getId());
            userRepository.save(user);
        }
    }
}
