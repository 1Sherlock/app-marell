package uz.soft.cosmos.appmarellserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.soft.cosmos.appmarellserver.entity.template.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Attachment")
public class Attachment extends AbstractEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String originalName;

    private long size;

    @Column(nullable = false)
    private String contentType;
}


