package uz.pdp.revolusiondemo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.*;
import uz.pdp.revolusiondemo.enums.OrderStatus;
import uz.pdp.revolusiondemo.model.templates.AbsIntEntity;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
public class Order extends AbsIntEntity {
    @ManyToOne
    private User user;

    @ManyToOne
    private Room room;

    private LocalDate startAt;

    private LocalDate endAt;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private String description;


}
