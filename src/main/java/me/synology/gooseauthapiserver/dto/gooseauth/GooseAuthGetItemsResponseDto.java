package me.synology.gooseauthapiserver.dto.gooseauth;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GooseAuthGetItemsResponseDto {

  private String name;

  private String username;

  private String password;

  private List<String> uri;

  private String folder;

  private String notes;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
  private LocalDateTime updateDate;
}
