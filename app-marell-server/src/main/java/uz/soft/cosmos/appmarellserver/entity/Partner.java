package uz.soft.cosmos.appmarellserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.soft.cosmos.appmarellserver.entity.template.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Partner extends AbstractEntity {
    private String name;

    @Column(columnDefinition = "text")
    private String description;

}

