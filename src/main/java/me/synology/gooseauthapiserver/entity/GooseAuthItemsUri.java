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

@Builder
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "goose_auth_items_uri")
public class GooseAuthItemsUri extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false)
  private Long uriIdentity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "goose_auth_items_item_identity")
  private GooseAuthItems gooseAuthItems;

  @Column(nullable = false, length = 350)
  private String uri;

  @Column(nullable = false, length = 100)
  private String createUser;

  @Column(nullable = false, length = 100)
  private String updateUser;

  public void updateItemUri(String uri) {
    this.uri = uri;
  }
}
