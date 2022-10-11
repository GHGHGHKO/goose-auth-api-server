package me.synology.gooseauthapiserver.dto.gooseauth;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DeleteItemUrisResponseDto {

  private Long itemIdentity;

  private String name;

  private String userName;

  private String userPassword;

  private String folder;

  private String notes;

  private List<UrisResponseDto> uris;
}
