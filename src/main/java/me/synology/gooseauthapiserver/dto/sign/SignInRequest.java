package me.synology.gooseauthapiserver.dto.sign;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class SignInRequest {

  @Email
  @NotNull
  private String userEmail;

  @NotNull
  private String userPassword;
}
