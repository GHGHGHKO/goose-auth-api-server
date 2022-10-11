package me.synology.gooseauthapiserver.dto.gooseauth;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GooseAuthGetItemsResponseDto {

  private Long itemIdentity;

  private String name;

  private String userName;
}
