package me.synology.gooseauthapiserver.model.response;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidationCheckResult extends CommonResult {

  private List<FieldErrors> argumentsNotValid;

  @Getter
  @Setter
  public static class FieldErrors {
    private String field;
    private String value;
    private String reason;
  }
}
