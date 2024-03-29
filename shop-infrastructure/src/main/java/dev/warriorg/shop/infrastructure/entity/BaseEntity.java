package dev.warriorg.shop.infrastructure.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
    @Id
    private String uid;

    @Column(updatable = false)
    @CreatedBy
    private String createdBy;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @LastModifiedBy
    private String modifiedBy;

    @UpdateTimestamp
    private LocalDateTime modifiedAt;

    @PrePersist
    protected void preCreate() {

    }

    @PreUpdate
    protected void preUpdate() {

    }
}
