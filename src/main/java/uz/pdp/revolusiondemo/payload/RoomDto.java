package uz.pdp.revolusiondemo.payload;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.revolusiondemo.enums.RoomType;
import uz.pdp.revolusiondemo.model.Attachment;
import uz.pdp.revolusiondemo.model.Hotel;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoomDto {
    private Integer id;

    private Integer hotelId;

    private boolean busy;

    private List<Integer> attachmentIds;

    private RoomType type;

    private Integer number;

    private Double price;
}
