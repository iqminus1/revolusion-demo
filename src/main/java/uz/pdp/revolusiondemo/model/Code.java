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
public class Code extends AbsIntEntity {
    @ManyToOne
    private User user;

    private String code;

    private Integer attempt;
}
