package me.synology.gooseauthapiserver.advice;

public class EmailSignInFailedExceptionCustom extends RuntimeException{

  public EmailSignInFailedExceptionCustom(String message, Throwable t) {
    super(message, t);
  }

  public EmailSignInFailedExceptionCustom(String message) {
    super(message);
  }

  public EmailSignInFailedExceptionCustom() {
    super();
  }
}
