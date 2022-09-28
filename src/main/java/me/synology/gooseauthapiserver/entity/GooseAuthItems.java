package me.synology.gooseauthapiserver.entity;

import javax.persistence.Column;
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
import me.synology.gooseauthapiserver.dto.gooseauth.UpdateItemRequestDto;

@Builder
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "goose_auth_items")
public class GooseAuthItems extends BaseEntity {

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

  @Column(nullable = false, length = 100)
  private String updateUser;

  public void updateItem(UpdateItemRequestDto updateItemRequestDto) {
    this.name = updateItemRequestDto.getName();
    this.userName = updateItemRequestDto.getUserName();
    this.userPassword = updateItemRequestDto.getUserPassword();
    this.folder = updateItemRequestDto.getFolder();
    this.notes = updateItemRequestDto.getNotes();
  }
}
