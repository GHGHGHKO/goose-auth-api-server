package me.synology.gooseauthapiserver.dto.sign.gooseauth;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import me.synology.gooseauthapiserver.constants.RegularExpressionConstants;

@Getter
@Setter
public class GooseAuthSignUpRequestDto {

  @Email(regexp = RegularExpressionConstants.EMAIL_VALIDATION)
  @NotBlank
  private String userEmail;

  @Pattern(regexp = RegularExpressionConstants.PASSWORD_VALIDATION)
  @NotNull
  private String userPassword;

  @NotNull
  private String userNickname;

  private String passwordHint;
}
