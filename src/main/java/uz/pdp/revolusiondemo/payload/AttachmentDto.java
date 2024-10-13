package uz.pdp.revolusiondemo.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class AttachmentDto implements Serializable {
    private Integer id;
    private boolean deleted;
    private String name;
    private String originalName;
    private String path;
    private String contentType;
    private Long size;
}