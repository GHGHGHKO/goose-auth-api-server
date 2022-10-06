package me.synology.gooseauthapiserver.dto.gooseauth;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GooseAuthGetItemResponseDto {

  private Long itemIdentity;

  private String name;

  private String userName;

  private String userPassword;

  private String folder;

  private String notes;

  private List<UrisResponseDto> uris;
}
