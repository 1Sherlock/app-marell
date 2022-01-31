package uz.soft.cosmos.appmarellserver.projection;

import java.sql.Timestamp;
import java.util.UUID;

/**
 * Created by Sherlock on 30.01.2022.
 */
public interface CustomPartner {
    UUID getId();

    String getCategory();

    Timestamp getCreatedAt();

    String getDescription();

    String getName();

    AttachmentInfo getPhoto();

    interface AttachmentInfo {
        UUID getId();
    }
}
