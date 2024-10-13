package uz.pdp.revolusiondemo.model;

import jakarta.persistence.*;
import lombok.*;
import uz.pdp.revolusiondemo.enums.RoomType;
import uz.pdp.revolusiondemo.model.templates.AbsIntEntity;

import java.util.List;

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

    @ManyToMany
    private List<Attachment> attachments;

    @Enumerated(EnumType.STRING)
    private RoomType type;

    private Integer number;

    private Double price;
}
