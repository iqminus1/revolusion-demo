package uz.pdp.revolusiondemo.payload.in;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HotelCrudDto {
    @NotBlank
    private String name;

    @NotBlank
    private String phoneNumber;

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;
}
