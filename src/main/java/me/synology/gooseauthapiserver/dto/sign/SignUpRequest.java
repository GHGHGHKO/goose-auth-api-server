package me.synology.gooseauthapiserver.dto.sign;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import me.synology.gooseauthapiserver.constants.RegularExpressionConstants;

@Getter
public class SignUpRequest {

  @Email(regexp = RegularExpressionConstants.EMAIL_VALIDATION)
  @NotBlank
  private String userEmail;

  @NotBlank
  @Pattern(regexp = RegularExpressionConstants.PASSWORD_VALIDATION)
  private String userPassword;

  @NotNull
  private String userNickname;
}
