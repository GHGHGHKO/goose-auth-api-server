package me.synology.gooseauthapiserver.advice;

public class UserExistExceptionCustom extends RuntimeException {

  public UserExistExceptionCustom(String message, Throwable throwable) {
    super(message, throwable);
  }

  public UserExistExceptionCustom(String message) {
    super(message);
  }

  public UserExistExceptionCustom() {
    super();
  }
}
