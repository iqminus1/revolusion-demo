package uz.pdp.revolusiondemo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.*;
import uz.pdp.revolusiondemo.enums.RoomType;
import uz.pdp.revolusiondemo.model.templates.AbsIntEntity;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
public class Room extends AbsIntEntity {
    @ManyToOne
    private Hotel hotel;

    private boolean busy;

    @Enumerated(EnumType.STRING)
    private RoomType type;

    private Integer number;

    private Double price;
}
