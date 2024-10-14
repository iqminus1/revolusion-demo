package uz.pdp.revolusiondemo.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.revolusiondemo.enums.PaymentStatus;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentDto {
    private Integer id;
    private Integer orderId;
    private Double amount;
    private PaymentStatus status;

}
