package me.synology.gooseauthapiserver.advice;

import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import me.synology.gooseauthapiserver.model.response.CommonResult;
import me.synology.gooseauthapiserver.service.ResponseService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionAdvice {

  private final ResponseService responseService;

  @ExceptionHandler(UserNotFoundExceptionCustom.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  protected CommonResult userNotFoundException(HttpServletRequest request,
      UserNotFoundExceptionCustom exceptionCustom) {
    return responseService.getFailResult();
  }
}
