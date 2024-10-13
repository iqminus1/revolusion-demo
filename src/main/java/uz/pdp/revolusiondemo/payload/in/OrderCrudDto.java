package uz.pdp.revolusiondemo.payload.in;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.revolusiondemo.enums.OrderStatus;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderCrudDto {
    @NotNull
    private Integer roomId;

    @NotNull
    private LocalDate startAt;

    @NotNull
    private LocalDate endAt;

    @NotNull
    private OrderStatus status;

    private String description;

}
