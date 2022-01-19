package uz.soft.cosmos.appmarellserver.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.soft.cosmos.appmarellserver.entity.enums.ProductType;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqProduct {
    private UUID id;
    private String name;
    private UUID category;
    private String description;
    private Integer count;
    private Double price;
    private Double btc;
    private UUID photo;
    private ProductType type;
}
