package me.synology.gooseauthapiserver.dto.sign;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GooseAuthSignUpRequestDto {

  @Email
  @NotNull
  private String userEmail;

  @NotNull
  private String userPassword;

  @NotNull
  private String userNickname;

  private String passwordHint;
}
