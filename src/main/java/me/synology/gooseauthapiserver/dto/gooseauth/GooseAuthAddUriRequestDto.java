package me.synology.gooseauthapiserver.dto.gooseauth;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GooseAuthAddUriRequestDto {

  private List<String> uri;
}
