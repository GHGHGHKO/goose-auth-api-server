package me.synology.gooseauthapiserver.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "goose_auth_items")
public class GooseAuthItems {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false)
  private Long itemIdentity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_identity")
  private UserMaster userMaster;

  @Column(nullable = false)
  private String name;

  private String userName;

  private String userPassword;

  private String folder;

  @Column(length = 1000)
  private String notes;

  @Column(nullable = false, length = 100)
  private String createUser;

  @Column(nullable = false)
  private LocalDateTime createDate;

  @Column(nullable = false, length = 100)
  private String updateUser;

  @Column(nullable = false)
  private LocalDateTime updateDate;

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(
      name = "goose_auth_items_uri",
      joinColumns = @JoinColumn(name = "goose_auth_items_id")
  )
  @Column(name = "uri")
  private List<String> uri = new ArrayList<>();
}
