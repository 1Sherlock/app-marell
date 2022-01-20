package uz.soft.cosmos.appmarellserver.projection;

import org.springframework.beans.factory.annotation.Value;
import uz.soft.cosmos.appmarellserver.entity.enums.ProductType;

import java.sql.Timestamp;
import java.util.UUID;

public interface CustomProduct {
    UUID getId();

    Double getBtc();

    Integer getCount();

    Timestamp getCreatedAt();

    String getDescription();

    String getName();

    Double getPrice();

    ProductType getType();

    Timestamp getUpdatedAt();

    CategoryInfo getCategory();

    AttachmentInfo getPhoto();

    @Value("#{@ValueHelper.getPartner(target.createdBy.partnerId)}")
    String getPartner();

    interface CategoryInfo {
        UUID getId();

        String getName();
    }

    interface AttachmentInfo {
        UUID getId();
    }
}
