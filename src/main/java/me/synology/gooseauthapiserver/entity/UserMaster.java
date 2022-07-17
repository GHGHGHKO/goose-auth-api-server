package me.synology.gooseauthapiserver.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import java.time.LocalDateTime;

@Builder
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_master")
public class UserMaster {

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

  @Column(nullable = false, length = 100)
  private String createUser;

  @Column(nullable = false)
  private LocalDateTime createDate;

  @Column(nullable = false, length = 100)
  private String updateUser;

  @Column(nullable = false)
  private LocalDateTime updateDate;

}
