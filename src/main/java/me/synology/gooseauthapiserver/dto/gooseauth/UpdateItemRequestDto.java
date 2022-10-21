package me.synology.gooseauthapiserver.dto.gooseauth;

import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateItemRequestDto {

  @NotNull
  private String name;

  private String userName;

  private String userPassword;

  private String folder;

  private String notes;

  private List<UrisRequestDto> uris;
}
