package me.synology.gooseauthapiserver.dto.sign;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import me.synology.gooseauthapiserver.constants.RegularExpressionConstants;

@Getter
public class SignInRequest {

  @Email(regexp = RegularExpressionConstants.EMAIL_VALIDATION)
  @NotBlank
  private String userEmail;

  @Pattern(regexp = RegularExpressionConstants.PASSWORD_VALIDATION)
  @NotNull
  private String userPassword;
}
