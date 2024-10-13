package uz.pdp.revolusiondemo.payload.in;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
    @Min(1)
    @Max(5)
    private Integer number;

    private String description;
}
