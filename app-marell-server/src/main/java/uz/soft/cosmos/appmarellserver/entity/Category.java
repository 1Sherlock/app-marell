package uz.soft.cosmos.appmarellserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.soft.cosmos.appmarellserver.entity.template.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Created by Sherlock on 24.08.2021.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Category extends AbstractEntity {
    @Column(unique = true)
    private String name;
}
