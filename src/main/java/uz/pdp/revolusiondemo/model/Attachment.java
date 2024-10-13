package uz.pdp.revolusiondemo.model;

import jakarta.persistence.Entity;
import lombok.*;
import uz.pdp.revolusiondemo.model.templates.AbsIntEntity;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
public class Attachment extends AbsIntEntity implements Serializable {
    private String name;
    private String originalName;
    private String path;
    private String contentType;
    private Long size;
}