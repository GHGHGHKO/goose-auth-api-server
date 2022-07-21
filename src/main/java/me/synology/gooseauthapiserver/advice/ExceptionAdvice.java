package me.synology.gooseauthapiserver.advice;

import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import me.synology.gooseauthapiserver.model.response.CommonResult;
import me.synology.gooseauthapiserver.service.ResponseService;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionAdvice {

  private final ResponseService responseService;

  private final MessageSource messageSource;

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  protected CommonResult defaultException(HttpServletRequest request,
      Exception e) {
    return responseService.getFailResult(Integer.parseInt(getMessage("unKnown.code")),
        getMessage("unKnown.message")
    );
  }

  @ExceptionHandler(UserNotFoundExceptionCustom.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  protected CommonResult userNotFoundException(HttpServletRequest request,
      UserNotFoundExceptionCustom exceptionCustom) {
    return responseService.getFailResult(
        Integer.parseInt(getMessage("userNotFound.code")),
        getMessage("userNotFound.message")
    );
  }

  @ExceptionHandler(EmailSignInFailedExceptionCustom.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  protected CommonResult emailSignInFailed(HttpServletRequest request,
      EmailSignInFailedExceptionCustom exceptionCustom) {
    return responseService.getFailResult(
        Integer.parseInt(getMessage("emailSignInFailed.code")),
        getMessage("emailSignInFailed.message")
    );
  }

  private String getMessage(String code) {
    return getMessage(code, null);
  }

  private String getMessage(String code, Object[] args) {
    return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
  }
}
