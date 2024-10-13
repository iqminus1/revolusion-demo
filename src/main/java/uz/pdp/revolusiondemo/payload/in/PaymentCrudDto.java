package uz.pdp.revolusiondemo.payload.in;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentCrudDto {
    @NotNull
    private Integer orderId;

    @NotNull
    private Double amount;
}
