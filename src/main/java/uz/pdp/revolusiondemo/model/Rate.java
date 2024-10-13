package uz.pdp.revolusiondemo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import uz.pdp.revolusiondemo.model.templates.AbsIntEntity;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
public class Rate extends AbsIntEntity {
    @ManyToOne
    private User user;

    @ManyToOne
    private Room room;

    private Integer number;

    private String description;
}
