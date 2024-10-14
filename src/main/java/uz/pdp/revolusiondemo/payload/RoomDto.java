package uz.pdp.revolusiondemo.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.revolusiondemo.enums.RoomType;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoomDto {
    private Integer id;

    private Integer hotelId;


    private List<Integer> attachmentIds;

    private RoomType type;

    private Integer number;

    private Double price;
}
