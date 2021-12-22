package uz.soft.cosmos.appmarellserver.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import uz.soft.cosmos.appmarellserver.entity.Attachment;
import uz.soft.cosmos.appmarellserver.entity.AttachmentContent;

import java.util.Optional;
import java.util.UUID;

/**
 * Created by Sherlock on 15.04.2020.
 */
public interface AttachmentContentRepository extends JpaRepository<AttachmentContent, UUID> {
    AttachmentContent getByAttachment(Attachment attachment);

    Optional<AttachmentContent> findByAttachment(Attachment attachment);
}
