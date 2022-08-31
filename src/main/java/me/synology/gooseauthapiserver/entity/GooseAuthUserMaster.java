package me.synology.gooseauthapiserver.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "goose_auth_user_master")
public class GooseAuthUserMaster {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false)
  private Long userIdentity;

  @Column(nullable = false, unique = true, length = 40)
  private String userEmail;

  @Column(nullable = false, length = 100)
  private String userPassword;

  @Column(nullable = false, length = 100)
  private String userNickname;

  @Column(length = 200)
  private String passwordHint;

  @Column(nullable = false, length = 100)
  private String createUser;

  @Column(nullable = false)
  private LocalDateTime createDate;

  @Column(nullable = false, length = 100)
  private String updateUser;

  @Column(nullable = false)
  private LocalDateTime updateDate;
}
