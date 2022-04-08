package com.kaan.kalaha.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.OffsetDateTime;

@Getter
@Setter
@EqualsAndHashCode
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "CREATED_BY", updatable = false)
    @CreatedBy
    private String createdBy;

    @Column(name = "CREATED_DATE", updatable = false, nullable = false)
    @CreationTimestamp
    private OffsetDateTime createdDate;

    @Column(name = "MODIFIED_BY")
    @LastModifiedBy
    private String modifiedBy;

    @Column(name = "UPDATED_DATE")
    @UpdateTimestamp
    private OffsetDateTime updatedDate;
}
