package uz.pdp.revolusiondemo.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HotelDto {
    private Integer id;

    private String name;

    private String phoneNumber;

    private double latitude;

    private double longitude;
}
