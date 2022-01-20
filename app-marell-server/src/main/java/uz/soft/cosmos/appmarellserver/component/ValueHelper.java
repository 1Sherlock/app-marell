package uz.soft.cosmos.appmarellserver.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uz.soft.cosmos.appmarellserver.repository.PartnerRepository;

import java.util.UUID;

@Component("ValueHelper")
public class ValueHelper {
    @Autowired
    PartnerRepository partnerRepository;


    public String getPartner(UUID partnerId) {
        return partnerRepository.getOne(partnerId).getName();
    }
}

