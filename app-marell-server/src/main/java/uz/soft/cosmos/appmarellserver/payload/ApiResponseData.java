package uz.soft.cosmos.appmarellserver.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponseData extends ApiResponse {
    private Object data;

    public ApiResponseData(String message, boolean success, Object data) {
        super(success, message);
        this.data = data;
    }
}