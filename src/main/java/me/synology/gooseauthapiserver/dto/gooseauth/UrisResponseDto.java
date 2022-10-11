package me.synology.gooseauthapiserver.dto.gooseauth;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UrisResponseDto {

  private Long uriIdentity;

  private String uri;
}
