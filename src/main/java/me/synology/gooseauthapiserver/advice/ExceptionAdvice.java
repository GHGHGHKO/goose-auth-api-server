package me.synology.gooseauthapiserver.advice;

import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import me.synology.gooseauthapiserver.model.response.CommonResult;
import me.synology.gooseauthapiserver.service.ResponseService;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionAdvice extends ResponseEntityExceptionHandler {

  private final ResponseService responseService;

  private final MessageSource messageSource;

  @ExceptionHandler(Exception.class)
  @ResponseBody
  protected ResponseEntity<CommonResult> defaultException(HttpServletRequest request) {
    return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(responseService.getFailResult(Integer.parseInt(getMessage("unKnown.code")),
            getMessage("unKnown.message")
        ));
  }

  @ExceptionHandler(UserNotFoundExceptionCustom.class)
  @ResponseBody
  protected ResponseEntity<CommonResult> userNotFoundException(HttpServletRequest request) {
    return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(responseService.getFailResult(
            Integer.parseInt(getMessage("userNotFound.code")),
            getMessage("userNotFound.message")
        ));
  }

  @ExceptionHandler(EmailSignInFailedExceptionCustom.class)
  @ResponseBody
  protected ResponseEntity<CommonResult> emailSignInFailed(HttpServletRequest request) {
    return ResponseEntity
        .status(HttpStatus.UNAUTHORIZED)
        .body(responseService.getFailResult(
            Integer.parseInt(getMessage("emailSignInFailed.code")),
            getMessage("emailSignInFailed.message")
        ));
  }

  @ExceptionHandler(CommunicationExceptionCustom.class)
  @ResponseBody
  protected ResponseEntity<CommonResult> communicationException(HttpServletRequest request) {
    return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(responseService.getFailResult(
            Integer.parseInt(getMessage("communicationError.code")),
            getMessage("communicationError.message")
        ));
  }

  @ExceptionHandler(UserExistExceptionCustom.class)
  @ResponseBody
  protected ResponseEntity<CommonResult> userExistException(HttpServletRequest request) {
    return ResponseEntity
        .status(HttpStatus.CONFLICT)
        .body(responseService.getFailResult(
            Integer.parseInt(getMessage("existingUser.code")),
            getMessage("existingUser.message")
        ));
  }

  @ExceptionHandler(AuthenticationException.class)
  @ResponseBody
  public ResponseEntity<CommonResult> authenticationEntryPointException(
      HttpServletRequest request) {
    return ResponseEntity
        .status(HttpStatus.UNAUTHORIZED)
        .body(
            responseService.getFailResult(Integer.parseInt(getMessage("entryPointException.code")),
                getMessage("entryPointException.message")
            ));
  }

  @ExceptionHandler(AccessDeniedException.class)
  @ResponseBody
  public ResponseEntity<CommonResult> accessDeniedException() {
    return ResponseEntity
        .status(HttpStatus.FORBIDDEN)
        .body(
            responseService.getFailResult(Integer.parseInt(getMessage("accessDenied.code")),
                getMessage("accessDenied.message")
            ));
  }

  private String getMessage(String code) {
    return getMessage(code, null);
  }

  private String getMessage(String code, Object[] args) {
    return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
  }
}
