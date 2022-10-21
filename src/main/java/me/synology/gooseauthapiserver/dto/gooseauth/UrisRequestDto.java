package me.synology.gooseauthapiserver.dto.gooseauth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UrisRequestDto {

  private Long uriIdentity;

  private String uri;
}
