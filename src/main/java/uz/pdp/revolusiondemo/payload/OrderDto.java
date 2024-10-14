package uz.pdp.revolusiondemo.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.revolusiondemo.enums.OrderStatus;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDto {
    private Integer id;
    private Integer userId;

    private Integer roomId;

    private LocalDate startAt;

    private LocalDate endAt;

    private OrderStatus status;

    private String description;

}
