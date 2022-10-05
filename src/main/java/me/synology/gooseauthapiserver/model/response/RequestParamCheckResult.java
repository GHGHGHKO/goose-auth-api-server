package me.synology.gooseauthapiserver.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestParamCheckResult extends CommonResult {

  private ParameterError parameterNotValid;

  @Getter
  @Setter
  public static class ParameterError {

    private String parameterName;

    private String message;
  }
}
