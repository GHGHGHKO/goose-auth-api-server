package me.synology.gooseauthapiserver.dto.sign.gooseauth;

import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddItemRequestDto {

  @NotNull
  private String name;

  private String userName;

  private String userPassword;

  private String folder;

  private String notes;

  private List<String> uri;
}
