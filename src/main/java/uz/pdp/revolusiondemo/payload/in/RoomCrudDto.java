package uz.pdp.revolusiondemo.payload.in;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.revolusiondemo.enums.RoomType;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoomCrudDto {
    @NotNull
    private Integer hotelId;

    @NotNull
    private RoomType type;

    private List<Integer> attachmentIds;

    @NotNull
    private Integer number;

    @NotNull
    private Double price;
}
