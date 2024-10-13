package uz.pdp.revolusiondemo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
import uz.pdp.revolusiondemo.model.templates.AbsIntEntity;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
public class Hotel extends AbsIntEntity {
    private String name;

    private String phoneNumber;

    @Column(unique = true)
    private double latitude;

    @Column(unique = true)
    private double longitude;
}
