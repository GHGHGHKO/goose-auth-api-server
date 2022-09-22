package me.synology.gooseauthapiserver.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@Getter
@MappedSuperclass
public class BaseEntity {

  @CreatedDate
  @Column(nullable = false, updatable = false)
  private LocalDateTime createDate;

  @LastModifiedDate
  @Column(nullable = false)
  private LocalDateTime updateDate;
}
