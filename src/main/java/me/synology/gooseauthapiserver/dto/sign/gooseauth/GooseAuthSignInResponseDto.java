package me.synology.gooseauthapiserver.dto.sign.gooseauth;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GooseAuthSignInResponseDto {

  private String userEmail;

  private String passwordHint;

  private String token;
}
