package uz.pdp.revolusiondemo.model.templates;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.sql.Timestamp;

@MappedSuperclass
@Getter
public abstract class AbsDateEntity implements Serializable {
    @CreatedDate
    @Column(updatable = false)
    private Timestamp createAt;

    @LastModifiedDate
    private Timestamp updateAt;
}