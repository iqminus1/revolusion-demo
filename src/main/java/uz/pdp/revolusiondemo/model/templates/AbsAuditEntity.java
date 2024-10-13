package uz.pdp.revolusiondemo.model.templates;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbsAuditEntity extends AbsDateEntity implements Serializable {
    @CreatedBy
    @Column(updatable = false)
    private Integer createBy;

    @LastModifiedBy
    private Integer updateBy;
}