package me.synology.gooseauthapiserver.service;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.synology.gooseauthapiserver.model.response.CommonResult;
import me.synology.gooseauthapiserver.model.response.ListResult;
import me.synology.gooseauthapiserver.model.response.SingleResult;
import org.springframework.stereotype.Service;

@Service
public class ResponseService {

  @Getter
  @RequiredArgsConstructor
  @AllArgsConstructor
  private enum CommonResponse {
    SUCCESS(0, "성공하였습니다."),
    FAIL(-1, "실패하였습니다.");

    private int code;
    private String message;
  }

  private void setSuccessResult(CommonResult commonResult) {
    commonResult.setSuccess(true);
    commonResult.setCode(CommonResponse.SUCCESS.getCode());
    commonResult.setMessage(CommonResponse.SUCCESS.getMessage());
  }

  private void setFailResult(CommonResult commonResult) {
    commonResult.setSuccess(false);
    commonResult.setCode(CommonResponse.FAIL.getCode());
    commonResult.setMessage(CommonResponse.FAIL.getMessage());
  }

  public CommonResult getSuccessResult() {
    CommonResult commonResult = new CommonResult();
    setSuccessResult(commonResult);
    return commonResult;
  }

  public CommonResult getFailResult() {
    CommonResult commonResult = new CommonResult();
    setFailResult(commonResult);
    return commonResult;
  }

  public <T> SingleResult<T> getSingleResult(T data) {
    SingleResult<T> singleResult = new SingleResult<>();
    singleResult.setData(data);
    setSuccessResult(singleResult);
    return singleResult;
  }

  public <T> ListResult<T> getListResult(List<T> list) {
    ListResult<T> listResult = new ListResult<>();
    listResult.setList(list);
    setSuccessResult(listResult);
    return listResult;
  }
}
