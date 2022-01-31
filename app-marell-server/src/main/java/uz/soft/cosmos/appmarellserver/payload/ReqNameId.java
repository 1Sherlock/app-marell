package uz.soft.cosmos.appmarellserver.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqNameId {
    private String name;
    private UUID id;

    private UUID photo;
    private String category;
    private String description;

    public ReqNameId(String name, UUID id) {
        this.name = name;
        this.id = id;
    }
}
