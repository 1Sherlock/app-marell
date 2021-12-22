package uz.soft.cosmos.appmarellserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.soft.cosmos.appmarellserver.entity.Attachment;

import java.util.List;
import java.util.UUID;

//@RepositoryRestResource(path = "attachment")
public interface AttachmentRepository extends JpaRepository<Attachment, UUID> {
    @Override
//    @RestResource(exported = false)
    <S extends Attachment> S save(S s);



    @Override
//    @RestResource(exported = false)
    <S extends Attachment> List<S> saveAll(Iterable<S> iterable);

    @Override
//    @RestResource(exported = false)
    void flush();

    @Override
//    @RestResource(exported = false)
    <S extends Attachment> S saveAndFlush(S s);

    @Override
//    @RestResource(exported = false)
    void deleteInBatch(Iterable<Attachment> iterable);

    @Override
//    @RestResource(exported = false)
    void deleteAllInBatch();

    @Override
//    @RestResource(exported = false)
    long count();

    @Override
//    @RestResource(exported = false)
    void deleteById(UUID uuid);

    @Override
//    @RestResource(exported = false)
    void delete(Attachment attachment);

    @Override
//    @RestResource(exported = false)
    void deleteAll(Iterable<? extends Attachment> iterable);

    @Override
//    @RestResource(exported = false)
    void deleteAll();
}
