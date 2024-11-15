package chien.demo.shopdemo.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Abstract entity.
 */
@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
//@EqualsAndHashCode(callSuper = false)
public class AbstractAuditEntity { // implements Serializable
    //private static final long serialVersionUID = 1L;

    @CreatedBy
    @Column(name = "created_by", updatable = false)
    protected String createdBy;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_date", updatable = false)
    protected Date createdDate;

    @LastModifiedBy
    @Column(name = "updated_by")
    protected String modifiedBy;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "updated_date")
    protected Date modifiedDate;
    //
    //@PreUpdate
    //@PrePersist
    //private void setDefaultVal() {
    //    if (Objects.isNull(this.createdBy)) {
    //        this.createdBy = super.getClass().getSimpleName();
    //    }
    //
    //    if (Objects.isNull(this.createdDate)) {
    //        this.createdDate = new Date();
    //    }
    //
    //    if (Objects.isNull(this.modifiedBy)) {
    //        this.modifiedBy = super.getClass().getSimpleName();
    //    }
    //
    //    if (Objects.isNull(this.modifiedDate)) {
    //        this.modifiedDate = new Date();
    //    }
    //}
}
