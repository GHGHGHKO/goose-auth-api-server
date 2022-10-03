package me.synology.gooseauthapiserver.dto.gooseauth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UrisResponseDto {

  private Long uriIdentity;

  private String uri;
}
