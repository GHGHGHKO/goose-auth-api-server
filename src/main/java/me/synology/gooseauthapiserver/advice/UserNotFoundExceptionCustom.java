package me.synology.gooseauthapiserver.advice;

public class UserNotFoundExceptionCustom extends RuntimeException {

  public UserNotFoundExceptionCustom(String message, Throwable throwable) {
    super(message, throwable);
  }

  public UserNotFoundExceptionCustom(String message) {
    super(message);
  }

  public UserNotFoundExceptionCustom() {
    super();
  }
}
