package uz.soft.cosmos.appmarellserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.soft.cosmos.appmarellserver.entity.enums.ProductType;
import uz.soft.cosmos.appmarellserver.entity.template.AbstractEntity;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Sherlock on 24.08.2021.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product extends AbstractEntity {

//    @Column(unique = true)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Category category;

    @Column(columnDefinition = "text")
    private String description;

    private Integer count;

    private Double price;

    private Double btc;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Attachment photo;

    @Enumerated
    private ProductType type;
}
