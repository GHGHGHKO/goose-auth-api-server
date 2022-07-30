package me.synology.gooseauthapiserver.advice;


public class AuthenticationEntryPointExceptionCustom extends RuntimeException {

  public AuthenticationEntryPointExceptionCustom(String message, Throwable throwable) {
    super(message, throwable);
  }

  public AuthenticationEntryPointExceptionCustom(String message) {
    super(message);
  }

  public AuthenticationEntryPointExceptionCustom() {
    super();
  }
}
