package me.synology.gooseauthapiserver.dto.gooseauth;

import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UpdateItemRequestDto {

  @NotNull
  private String name;

  private String userName;

  private String userPassword;

  private String folder;

  private String notes;

  private List<String> uri;
}
