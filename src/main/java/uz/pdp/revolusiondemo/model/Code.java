package uz.pdp.revolusiondemo.model;

import jakarta.persistence.Entity;
import lombok.*;
import uz.pdp.revolusiondemo.model.templates.AbsIntEntity;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
public class Code extends AbsIntEntity {
    private String email;

    private String code;

    private Integer attempt;

    private long expireAt;
}
