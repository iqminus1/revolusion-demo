package uz.pdp.revolusiondemo.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SignInDto {
    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
