package me.synology.gooseauthapiserver.dto.sign.gooseauth;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GooseAuthSignInRequestDto {

  @Email
  @NotNull
  private String userEmail;

  @NotNull
  private String userPassword;
}
