package uz.pdp.revolusiondemo.payload;

import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.revolusiondemo.model.Room;
import uz.pdp.revolusiondemo.model.User;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RateDto {
    private Integer id;

    private Integer userId;

    private Integer roomId;

    private Integer number;

    private String description;
}
