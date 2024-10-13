package uz.pdp.revolusiondemo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.*;
import uz.pdp.revolusiondemo.model.templates.AbsIntEntity;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
public class Payment extends AbsIntEntity {
    @OneToOne
    private Order order;

    private Double amount;
}
