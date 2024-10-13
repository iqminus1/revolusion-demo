package uz.pdp.revolusiondemo.payload.in;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RateCrudDto {
    @NotNull
    private Integer roomId;

    @NotNull
    private Integer number;

    private String description;
}
