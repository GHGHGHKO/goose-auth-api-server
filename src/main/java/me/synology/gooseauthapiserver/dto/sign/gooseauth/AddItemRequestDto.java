package me.synology.gooseauthapiserver.dto.sign.gooseauth;

import java.util.List;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import me.synology.gooseauthapiserver.constants.RegularExpressionConstants;

@Getter
@Setter
public class AddItemRequestDto {

  @Email(regexp = RegularExpressionConstants.EMAIL_VALIDATION)
  @NotBlank
  private String userEmail;

  @NotNull
  private String name;

  private String userName;

  private String userPassword;

  private String folder;

  private String notes;

  private List<String> uri;
}
