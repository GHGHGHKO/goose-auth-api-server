package me.synology.gooseauthapiserver.dto.gooseauth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GooseAuthGetItemsResponseDto {

  private Long itemIdentity;

  private String name;

  private String userName;

  private String folder;
}
