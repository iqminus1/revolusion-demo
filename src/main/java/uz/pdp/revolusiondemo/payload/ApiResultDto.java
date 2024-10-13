package uz.pdp.revolusiondemo.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class ApiResultDto<T> {
    private boolean success;

    private T data;

    private Object error;

    public static <T> ApiResultDto<T> success(T data) {
        return ApiResultDto.<T>builder().success(true).data(data).build();
    }

    public static ApiResultDto<?> error(String errorMessage) {
        return ApiResultDto.<String>builder().error(errorMessage).build();
    }

    public static ApiResultDto<?> error(MethodArgumentNotValidException exception) {
        Map<String, String> errorMap = exception.getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));

        return ApiResultDto.<Object>builder()
                .error(errorMap)
                .build();
    }
}
